package com.example.ksala.healthproject;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

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

    /* Animations */
    public static Animation inGoUp(int duration) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation inGoDown(int duration) {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(duration);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    public static Animation outGoUp(int duration) {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f);
        inFromRight.setDuration(duration);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation outGoDown(int duration) {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, +1.0f);
        outtoLeft.setDuration(duration);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }
}
