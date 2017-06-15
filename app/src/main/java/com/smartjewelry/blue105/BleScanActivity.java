package com.smartjewelry.blue105;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.smart.library.blue.BleDevice;
import com.smart.library.blue.BleScan;
import com.smart.library.permission.PermissionsManager;
import com.smart.library.permission.PermissionsResultAction;

import java.util.List;

public class BleScanActivity extends AppCompatActivity implements BleScan.BleScanImp {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mDeviceLv;
    private DeviceListAdapter mAdapter;
    private BleDevice mConnectDevice;
    private BleScan mBleScan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_scan);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBleScan = new BleScan(this, this);
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        mAdapter.clearDevices();

                        mBleScan.startScan();
                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(BleScanActivity.this, R.string.permission_deny, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new DeviceListAdapter(this);
        mDeviceLv = (ListView) findViewById(R.id.scan_lv);
        mDeviceLv.setAdapter(mAdapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mBleScan != null && !mBleScan.getScanState()) {
                    mBleScan.startScan();
                }
            }
        });

        mDeviceLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mBleScan != null) {
                    mBleScan.stopScan();
                }
                mConnectDevice = (BleDevice) mAdapter.getItem(i);
                String deviceAddress = mConnectDevice.device.getAddress();
                String deviceName = mConnectDevice.device.getName();

                Intent intent = new Intent(BleScanActivity.this, BleControlActivity.class);
                intent.putExtra(BleControlActivity.EXTRAS_DEVICE_NAME, deviceName);
                intent.putExtra(BleControlActivity.EXTRAS_DEVICE_ADDRESS, deviceAddress);
                BleScanActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_scan, menu);
        if (mBleScan != null && !mBleScan.getScanState()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                if (mBleScan != null) {
                    mBleScan.startScan();
                }
                mSwipeRefreshLayout.setRefreshing(true);
                invalidateOptionsMenu();
                break;
            case R.id.menu_stop:
                if (mBleScan != null) {
                    mBleScan.stopScan();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    @Override
    public void onScanStart() {
        mSwipeRefreshLayout.measure(0, 0);
        mSwipeRefreshLayout.setRefreshing(true);
        invalidateOptionsMenu();
    }

    @Override
    public void onScanStop() {
        mSwipeRefreshLayout.setRefreshing(false);
        invalidateOptionsMenu();
    }

    @Override
    public void onScanFail() {

    }

    @Override
    public void onScanDevices(List<BleDevice> unbindDevices, List<BleDevice> bindedDevices) {
        mAdapter.updateDevices(unbindDevices, bindedDevices);
    }
}
