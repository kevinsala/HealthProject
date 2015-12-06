package com.example.ksala.healthproject.Bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import com.example.ksala.healthproject.DataMessage;
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
            tmpSocket = device.createRfcommSocketToServiceRecord(BluetoothUtils.MY_UUID);
        } catch (IOException e) { }

        this.socket = tmpSocket;
    }

    public void run() {
        boolean done;

        done = connectSocket();
        if (!done) connectionError("connect-socket");

        done = setStreams();
        if (!done) connectionError("set-streams");

        done = receive();
        if (!done) connectionError("receive");
    }

    public boolean connectSocket() {
        try {
            socket.connect();
            handler.obtainMessage(BluetoothUtils.CONNECTION_STARTED_MSG).sendToTarget();
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

    private boolean waitToSize(String buffer, int size) {
        while (true) {
            String aux = readBuffer();
            if (aux == null) return false;
            else buffer = buffer.concat(aux);

            if (buffer.length() >= size) return true;
        }
    }

    public boolean receive() {
        String buffer = "";

        while (true) {
            if (!waitToSize(buffer, 5)) break;

            if (buffer.charAt(0) != 'M') break;
            int size = Integer.parseInt(buffer.substring(1, 5));

            if (!waitToSize(buffer, size + 5)) break;

            int current = 5;

            int func = Character.getNumericValue(buffer.charAt(current++));
            int type = Character.getNumericValue(buffer.charAt(current++));

            if (type == BluetoothUtils.DATA_MSG) {
                int start = current;
                while(buffer.charAt(current) != '!') ++current;
                int x = Integer.parseInt(buffer.substring(start, current));

                start = ++current;
                while(buffer.charAt(current) != 'X') ++current;
                int y = Integer.parseInt(buffer.substring(start, current));

                handler.obtainMessage(BluetoothUtils.DATA_MSG, func, 0, new DataMessage(x,y))
                        .sendToTarget();
            }
            else if (type == BluetoothUtils.END_MSG){
                handler.obtainMessage(BluetoothUtils.END_MSG, func, 0).sendToTarget();
            }
            else {
                Log.d(Utils.LOG_TAG, "Bluetooth: Incorrecte MSG type");
            }

            buffer = buffer.substring(5 + size);
        }
        return false;
    }

    public boolean send(int type, int func) {
        String msg = "M";
        msg += "0003";
        msg += Character.forDigit(func, 10);
        msg += Character.forDigit(type, 10);
        msg += 'X';
        return writeBuffer(msg);
    }

    private String readBuffer() {
        byte[] buffer = new byte[1024];
        int nBytes;

        try {
            nBytes = inputStream.read(buffer);
            return new String(buffer, 0, nBytes, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    private boolean writeBuffer(String msg) {
        try {
            byte [] buffer = msg.getBytes("UTF-8");
            outputStream.write(buffer);
        } catch (IOException e) {
            cancel();
            return false;
        }

        return true;
    }

    public void connectionError(String error) {
        cancel();
        handler.obtainMessage(BluetoothUtils.CONNECTION_LOST_MSG, error).sendToTarget();
        return;
    }

    public void cancel() {
        Log.d(Utils.LOG_TAG, "Bluetooth: Cancelling connection");
        try {
            socket.close();
        } catch (IOException e) { }
    }
}
