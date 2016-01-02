package com.example.ksala.healthproject.Activities;

import java.util.Set;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import com.example.ksala.healthproject.Bluetooth.*;
import com.example.ksala.healthproject.Fragments.*;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.*;
import com.example.ksala.healthproject.Views.CustomViewPager;
import com.example.ksala.healthproject.*;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    /* View Pager attributes */
    private static final int NUM_PAGES = 7;
    private static final int MAIN_PAGE = 3;
    private CustomViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private AbstractFragment fragments[];

    /* Bluetooth attributes */
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice = null;

    private Handler mainHandler;
    private ConnectionThread connectionThread;

    private int currentFunctionality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentFunctionality = Utils.NULL_FUNC;

        mainHandler = new Handler(Looper.getMainLooper(), this);

        /* Bluetooth configuration */
        //configureBluetooth();

        /* View pager configuration */
        configurePages();
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        setCurrentPage(MAIN_PAGE);
        ((PagerTitleStrip) findViewById(R.id.pagerTitleStrip)).setTextSpacing(540);
        ((PagerTitleStrip) findViewById(R.id.pagerTitleStrip)).setGravity(Gravity.CENTER);
    }


    public void configureBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) finish();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, BluetoothUtils.ENABLE_REQUEST);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (BluetoothUtils.DEVICE_NAME.equals(device.getName())) {
                    Log.d(Utils.LOG_TAG, "Bluetooth device found in paired devices");
                    bluetoothDevice = device;
                    connectToDevice();
                    return;
                }
            }
        }
        Log.d(Utils.LOG_TAG, "Bluetooth device not paired! Please connect to it through Settings");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothUtils.ENABLE_REQUEST) {
            if (resultCode != RESULT_OK) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, BluetoothUtils.ENABLE_REQUEST);
            }
        }
    }

    public void connectToDevice() {
        connectionThread = new ConnectionThread(bluetoothDevice, mainHandler);
        connectionThread.start();
    }

    public void startMeasurement(int functionality) {
        boolean sent = connectionThread.send(BluetoothUtils.START_MSG, functionality);
        if (!sent) Log.d(Utils.LOG_TAG, "Start measurement failed");
        currentFunctionality = functionality;
    }

    public void cancelMeasurement(int functionality) {
        boolean sent = connectionThread.send(BluetoothUtils.END_MSG, functionality);
        if (!sent) Log.d(Utils.LOG_TAG, "Cancel measurement failed");
        currentFunctionality = Utils.NULL_FUNC;
    }

    public void endMeasurement(int functionality) {
        currentFunctionality = Utils.NULL_FUNC;
    }

    public void setSwipeable(boolean swipeable) {
        viewPager.setSwipeable(swipeable);
    }

    private void configurePages() {
        fragments = new AbstractFragment[NUM_PAGES];
        fragments[Utils.RESPIRATORY_RATE_FUNC]  = new RespiratoryRateFragment();
        fragments[Utils.BLOOD_PRESSURE_FUNC]    = new BloodPressureFragment();
        fragments[Utils.ECG_FUNC]               = new ECGFragment();
        fragments[MAIN_PAGE]                    = new FunctionFragment();
        fragments[Utils.LUNG_CAPACITY_FUNC]     = new LungCapacityFragment();
        fragments[Utils.TEMPERATURE_FUNC]       = new TemperatureFragment();
        fragments[Utils.OXYGEN_SATURATION_FUNC] = new OxygenSaturationFragment();
    }

    public int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    public void setCurrentPage(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onBackPressed() {
        if (getCurrentPage() == MAIN_PAGE)
            super.onBackPressed();
        else {
            setSwipeable(true);
            setCurrentPage(MAIN_PAGE);
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        int func = msg.arg1;
        if (what == BluetoothUtils.CONNECTION_STARTED_MSG) {

        } else if (what == BluetoothUtils.DATA_MSG) {
            DataMessage data = (DataMessage) msg.obj;
            if (func == currentFunctionality) {

            }
        } else if (what == BluetoothUtils.CONNECTION_LOST_MSG) {

        } else return false;
        return true;
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(Utils.medicalNames[position]);
        }
    }
}