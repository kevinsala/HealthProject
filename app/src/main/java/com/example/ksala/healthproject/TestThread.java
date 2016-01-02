package com.example.ksala.healthproject;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;
import android.util.Log;
import com.example.ksala.healthproject.Bluetooth.BluetoothUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestThread extends Thread {
    private InputStream inputStream;
    private OutputStream outputStream;

    public TestThread(BluetoothDevice device, Handler handler) {

    }

    public void run() {
        receive();
    }

    public boolean receive() {
        String buffer = "";

        while (true) {

            if (buffer.charAt(0) != 'M') break;
            int size = Integer.parseInt(buffer.substring(1, 5));

            //if (!waitToSize(buffer, size + 5)) break;

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

                //handler.obtainMessage(BluetoothUtils.DATA_MSG, func, 0, new DataMessage(x,y))
                       // .sendToTarget();
            }
            else if (type == BluetoothUtils.END_MSG){
                Log.d(Utils.LOG_TAG, "Bluetooth: ");
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
        Log.d(Utils.LOG_TAG, msg);

        return true;
    }
}
