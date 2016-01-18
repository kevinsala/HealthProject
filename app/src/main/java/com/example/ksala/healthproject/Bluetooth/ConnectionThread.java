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
    private final BluetoothDevice device;
    private BluetoothSocket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ConnectionThread(BluetoothDevice device, Handler handler) {
        this.handler = handler;
        this.device = device;

        init();
    }

    private void init() {
        BluetoothSocket tmpSocket = null;

        try {
            tmpSocket = device.createRfcommSocketToServiceRecord(BluetoothUtils.MY_UUID);
        } catch (IOException e) {
            init();
        }

        this.socket = tmpSocket;
    }

    public void run() {
        boolean done;

        done = connectSocket();
        if (!done) connectionError("connect-socket");

        done = setStreams();
        if (!done) connectionError("set-streams");

        receive();
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

    private String readNumChars(String buffer, int size) {
        int sizeRead = buffer.length();
        while (sizeRead < size) {
            String aux = readBuffer((size - sizeRead) * 2);
            if (aux != null) {
                buffer = buffer.concat(aux);
                sizeRead += aux.length();
            }
        }
        return buffer;
    }

    public void receive() {
        String buffer = "";
        double x, y;

        while (true) {
            if (buffer.length() < 3) buffer = readNumChars(buffer, 3);

            assert(buffer.charAt(0) == 'M');
            int size = Integer.parseInt(buffer.substring(1, 3));
            if (buffer.length() < 3 + size) buffer = readNumChars(buffer, 3 + size);

            int current = 3;

            int func = Character.getNumericValue(buffer.charAt(current++));
            int type = Character.getNumericValue(buffer.charAt(current++));

            if (type == BluetoothUtils.DATA_MSG || type == BluetoothUtils.DATA_END_MSG) {
                int start = current;
                while (buffer.charAt(current) != '!') ++current;
                x = Double.parseDouble(buffer.substring(start, current));

                start = ++current;
                while(buffer.charAt(current) != 'X') ++current;
                if (start != current) y = Double.parseDouble(buffer.substring(start, current));
                else y = -1;

                handler.obtainMessage(type, func, 0, new DataMessage(x,y)).sendToTarget();
            }
            else if (type == BluetoothUtils.END_MSG){
                handler.obtainMessage(type, func, 0).sendToTarget();
            }
            else {
                Log.d(Utils.LOG_TAG, "Bluetooth: Incorrect MSG type");
            }

            Log.d(Utils.LOG_TAG, "Buffer: " + buffer);

            buffer = buffer.substring(3 + size);
        }
    }

    public boolean send(int type, int func) {
        String msg = "M";
        msg = msg.concat(new Character(Character.forDigit(func, 10)).toString());
        msg = msg.concat(new Character(Character.forDigit(type, 10)).toString());
        msg = msg.concat("X");

        Log.d(Utils.LOG_TAG, "MSG SENT: " + msg);
        return writeBuffer(msg);
    }

    private String readBuffer(int size) {
        byte[] buffer = new byte[1024];
        int nBytes;

        try {
            nBytes = inputStream.read(buffer, 0, size);
            return new String(buffer, 0, nBytes, "UTF-8");
        } catch (IOException e) {
            connectionError("receive");
            return null;
        }
    }

    private boolean writeBuffer(String msg) {
        try {
            byte [] buffer = msg.getBytes();
            Log.d(Utils.LOG_TAG, "MSG BYTES: " + buffer.length);
            outputStream.write(buffer);
            return true;
        } catch (IOException e) {
            connectionError("receive");
            return false;
        }
    }

    public void connectionError(String error) {
        cancel();
        handler.obtainMessage(BluetoothUtils.CONNECTION_LOST_MSG, error).sendToTarget();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init();
        run();
    }

    public void cancel() {
        Log.d(Utils.LOG_TAG, "Bluetooth: Cancelling connection");
        try {
            socket.close();
        } catch (IOException e) { }
    }
}
