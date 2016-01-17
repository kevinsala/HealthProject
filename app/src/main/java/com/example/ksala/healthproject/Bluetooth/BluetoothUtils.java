package com.example.ksala.healthproject.Bluetooth;

import java.util.UUID;

public class BluetoothUtils {

    /* External Message types */
    public static final int START_MSG = 0;
    public static final int DATA_MSG = 1;
    public static final int END_MSG = 2;
    public static final int DATA_END_MSG = 3;

    /* Internal Message types */
    public static final int CONNECTION_STARTED_MSG = 99;
    public static final int CONNECTION_LOST_MSG = 101;

    /* Target bluetooth device name */
    public static final String DEVICE_NAME = "OTTOWISKIS";

    /* Request code to enable bluetooth feature */
    public static final int ENABLE_REQUEST = 0;

    /* UUID of the Android device */
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
}
