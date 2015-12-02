package com.example.ksala.healthproject.Activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.ksala.healthproject.Fragments.AbstractFragment;
import com.example.ksala.healthproject.Fragments.CommonFragment;
import com.example.ksala.healthproject.Concurrency.ConnectionThread;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.BloodPressureFragment;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.ECGFragment;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.LungCapacityFragment;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.OxygenSaturationFragment;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.RespiratoryRateFragment;
import com.example.ksala.healthproject.Fragments.ConcreteFragments.TemperatureFragment;
import com.example.ksala.healthproject.Views.CustomViewPager;
import com.example.ksala.healthproject.Fragments.FunctionFragment;
import com.example.ksala.healthproject.R;
import com.example.ksala.healthproject.Utils;

import java.util.Set;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if (bluetoothAdapter == null) displayBtNotSupportedDialog();
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Utils.BT_ENABLE_REQUEST);
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (Utils.BT_DEVICE_NAME.equals(device.getName())) {
                    Log.d(Utils.LOG_TAG, "Bluetooth device found in paired devices");
                    bluetoothDevice = device;
                    connectToDevice();
                    break;
                }
            }
        }
        else {
            Log.d(Utils.LOG_TAG, "Starting discovery");
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);
            bluetoothAdapter.startDiscovery();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            try {
                unregisterReceiver(receiver);
            } catch (Exception e) { }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Utils.BT_ENABLE_REQUEST) {
            if (resultCode != RESULT_OK) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Utils.BT_ENABLE_REQUEST);
            }
        }
    }

    public void displayBtNotSupportedDialog() {

    }

    public void connectToDevice() {
        connectionThread = new ConnectionThread(bluetoothDevice, mainHandler);
        connectionThread.start();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (Utils.BT_DEVICE_NAME.equals(device.getName())) {
                    bluetoothDevice = device;
                    Log.d(Utils.LOG_TAG, "Bluetooth device discovered");
                    bluetoothAdapter.cancelDiscovery();
                    connectToDevice();
                }
            }
        }
    };

    public void setSwipeable(boolean swipeable) {
        viewPager.setSwipeable(swipeable);
    }

    private void configurePages() {
        fragments = new AbstractFragment[NUM_PAGES];
        fragments[0] = new RespiratoryRateFragment();
        fragments[1] = new BloodPressureFragment();
        fragments[2] = new ECGFragment();
        fragments[3] = new FunctionFragment();
        fragments[4] = new LungCapacityFragment();
        fragments[5] = new TemperatureFragment();
        fragments[6] = new OxygenSaturationFragment();
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
        if (what == Utils.BT_CONNECTION_STARTED_MSG) {

        } else if (what == Utils.BT_READ_MSG) {

        } else if (what == Utils.BT_CONNECTION_LOST_MSG) {

        } else {
            return false;
        }
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