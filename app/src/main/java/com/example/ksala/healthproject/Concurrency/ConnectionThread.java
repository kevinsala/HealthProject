package com.example.ksala.healthproject.Concurrency;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.example.ksala.healthproject.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ksala on 17/11/2015.
 */
public class ConnectionThread extends Thread {
    private final Handler handler;
    private final BluetoothSocket socket;
    private final BluetoothDevice device;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ConnectionThread(BluetoothDevice device, Handler handler) {
        BluetoothSocket tmpSocket = null;

        this.handler = handler;
        this.device = device;

        try {
            tmpSocket = device.createRfcommSocketToServiceRecord(Utils.MY_UUID);
        } catch (IOException e) { }

        this.socket = tmpSocket;
    }

    public void run() {
        boolean done;

        done = connectSocket();
        if (!done) connectionError("connect-socket");

        setStreams();
        if (!done) connectionError("set-streams");

        sendAndReceive();
        if (!done) connectionError("send-and-receive");
    }

    public boolean connectSocket() {
        try {
            socket.connect();
            handler.obtainMessage(Utils.BT_CONNECTION_STARTED_MSG).sendToTarget();
        } catch (IOException connectException) {
            return false;
        }

        return true;
    }

    public boolean setStreams() {
        InputStream tmpInput = null;
        OutputStream tmpOutput = null;

        try {
            tmpInput = socket.getInputStream();
            tmpOutput = socket.getOutputStream();
        } catch (IOException e) {
            return false;
        }

        inputStream = tmpInput;
        outputStream = tmpOutput;
        return true;
    }

    /* TODO: Process the messages */
    public boolean sendAndReceive() {
        byte[] buffer = new byte[1024];
        int bytes;

        while (true) {
            try {
                bytes = inputStream.read(buffer);
                handler.obtainMessage(Utils.BT_READ_MSG).sendToTarget();
            } catch (IOException e) {
                break;
            }
        }
        return false;
    }

    public boolean write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            cancel();
            return true;
        }

        return true;
    }

    public void connectionError(String error) {
        cancel();
        handler.obtainMessage(Utils.BT_CONNECTION_LOST_MSG, error).sendToTarget();
        return;
    }

    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }
}
