package com.example.ksala.healthproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    /* View Pager attributes */
    private static final int NUM_PAGES = 7;
    private static final int MAIN_PAGE = 3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private AbstractFragment fragments[];

    /* Bluetooth attributes */
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Bluetooth configuration */
        //configureBluetooth();

        /* View pager configuration */
        configurePages();
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
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
        ConnectThread thread = new ConnectThread(bluetoothDevice);
        thread.start();
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

    private void configurePages() {
        fragments = new AbstractFragment[NUM_PAGES];
        fragments[0] = new DummyFragment();
        fragments[1] = new DummyFragment();
        fragments[2] = new DummyFragment();
        fragments[3] = new FunctionFragment();
        fragments[4] = new DummyFragment();
        fragments[5] = new DummyFragment();
        fragments[6] = new DummyFragment();
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
            setCurrentPage(MAIN_PAGE);
        }
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