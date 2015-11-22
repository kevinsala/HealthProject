package com.example.ksala.healthproject;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

/**
 * Created by ksala on 17/11/2015.
 */
public class ConnectThread extends Thread {
    private final BluetoothSocket socket;
    private final BluetoothDevice device;

    public ConnectThread(BluetoothDevice btDevice) {
        // Use a temporary object that is later assigned to socket,
        // because socket is final
        BluetoothSocket tmp = null;
        this.device = btDevice;
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(Utils.MY_UUID);
        } catch (IOException e) { }
        Log.d(Utils.LOG_TAG, "Thread created");
        this.socket = tmp;
    }

    public void run() {
        Log.d(Utils.LOG_TAG, "Running!");
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            socket.connect();
            Log.d(Utils.LOG_TAG, "Device connected!");
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            try {
                socket.close();
            } catch (IOException closeException) { }
            return;
        }
    }

    /** Will cancel an in-progress connection, and close the socket */
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }

}
