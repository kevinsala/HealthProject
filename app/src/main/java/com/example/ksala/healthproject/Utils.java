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

    public static final String BT_DEVICE_NAME = "OTOWISKIS";
    public static final int BT_ENABLE_REQUEST = 0;
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final int BT_CONNECTION_STARTED_MSG = 99;
    public static final int BT_READ_MSG = 100;
    public static final int BT_CONNECTION_LOST_MSG = 101;
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

    public static final String RESPIRATORY_RATE_INSTRUCTIONS =
            "1. Colocar el sensor de temperatura davant la boca i respirar normal.\n\n" +
            "2. Esperar fins que s'acabi el procés.\n\n" +
            "3. En la pantalla es mostrarà la cadència respiratoria mesurada.";

    public static final String BLOOD_PRESSURE_INSTRUCTIONS =
            "1. Ligar-se el braçalet de pressió a la part superior del braç.\n\n" +
            "2. Esperar fins que s'acabi el procés.\n\n" +
            "3. En la pantalla es mostrarà la pressió sanguinia mesurada.";

    public static final String ECG_INSTRUCTIONS =
            "1. Colocar cada dit índex de la mà sobre una placa metàlica.\n\n" +
            "2. Mantingui els dits en les plaques metàliques fins que vulgui aturar la prova.\n\n" +
            "3. En la pantalla es mostrarà l'electrocardiograma sencer.";

    public static final String LUNG_CAPACITY_INSTRUCTIONS =
            "1. Agafar aire i bufar a través del tub tant fort com sigui possible.\n\n" +
            "2. Esperar fins que s'acabi el procés.\n\n" +
            "3. En la pantalla es mostraran els resultats de la capacitat pulmonar mesurada.";

    public static final String TEMPERATURE_INSTRUCTIONS =
            "1. Possar-se el sensor de temperatura sota la aixella.\n\n" +
            "2. Esperar fins que s'acabi el procés.\n\n" +
            "3. En la pantalla es mostrarà la temperatura corporal mesurada.";

    public static final String OXYGEN_SATURATION_INSTRUCTIONS =
            "1. Colocar el dit índex en la pinça.\n\n" +
            "2. Esperar fins que s'acabi el procés.\n\n" +
            "3. En la pantalla es mostrarà l'oxigen en sang mesurat.";
}