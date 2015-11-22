package com.example.ksala.healthproject;

import java.util.UUID;

/**
 * Created by ksala on 12/11/2015.
 */
public class Utils {
    public static final String LOG_TAG = "LOG-PEC";

    /* TODO: Change name to OTOWISKIS */
    public static final String BT_DEVICE_NAME = "pec10f";
    public static final int BT_ENABLE_REQUEST = 0;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public static final int medicalIcons []= {
            R.mipmap.ecg,
            R.mipmap.lungcapacity,
            R.mipmap.bloodpressure,
            R.mipmap.temperature,
            R.mipmap.respiratoryrate,
            R.mipmap.o2saturation };

    public static final int medicalNames []= {
            R.string.respiratoryrate,
            R.string.bloodpressure,
            R.string.ecg,
            R.string.menu,
            R.string.lungcapacity,
            R.string.temperature,
            R.string.o2saturation};
}
