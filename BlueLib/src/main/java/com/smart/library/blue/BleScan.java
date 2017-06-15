package com.smart.library.blue;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;

public class BleScan {

    private List<BleDevice> mBindedDevices = new ArrayList<>();

    private List<BleDevice> mUnbindDevices = new ArrayList<>();

    private int CONNECT_TIMEOUT = 10 * 1000;

    private Handler mHandler = new Handler();

    private BluetoothAdapter mBleAdapter;

    private boolean isScanning;

    private BleScanImp mBleScanImp;

    private Context mContext;

    public interface BleScanImp {
        void onScanStart();

        void onScanStop();

        void onScanFail();

        void onScanDevices(List<BleDevice> unbindDevices, List<BleDevice> bindedDevices);
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(final int callbackType, final ScanResult result) {
            BleLogs.i("onScanResult:" + result.toString());
        }

        @Override
        public void onBatchScanResults(final List<ScanResult> results) {
            BleLogs.i("onBatchScanResults:" + results.toString());
            getUnbondedDevices(results);
            if (mBleScanImp != null) {
                mBleScanImp.onScanDevices(mUnbindDevices, mBindedDevices);
            }
        }

        @Override
        public void onScanFailed(final int errorCode) {
            BleLogs.i("onScanFailed:" + errorCode);
            if (mBleScanImp != null) {
                mBleScanImp.onScanFail();
            }
        }
    };

    public BleScan(Context context, BleScanImp imp) {
        this.mContext = context;
        this.mBleScanImp = imp;
        init(context);
    }

    private void init(Context context) {
        BluetoothManager manager = (BluetoothManager) context.getSystemService(context.BLUETOOTH_SERVICE);
        mBleAdapter = manager.getAdapter();
        if (mBleAdapter != null) {
            if (!mBleAdapter.isEnabled()) {
                mBleAdapter.enable();
            } else {
//                startScan(context);
            }
        }
        mBindedDevices.clear();
        mUnbindDevices.clear();
    }

    public void startScan() {
        mUnbindDevices.clear();
        if (!BleUtils.isOpenBlue(mContext)) {
            isScanning = false;
            BleUtils.isOpenBlue(mContext);
            if (mBleScanImp != null) {
                mBleScanImp.onScanFail();
            }
            return;
        }
        getBondedDevices();
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(1000)
                .setUseHardwareBatchingIfSupported(false)
                .build();
        List<ScanFilter> filters = new ArrayList<>();
        filters.add(new ScanFilter.Builder().setServiceUuid(null).build());
        scanner.startScan(filters, settings, mScanCallback);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isScanning) {
                    stopScan();
                }
            }
        }, CONNECT_TIMEOUT);
        isScanning = true;
        if (mBleScanImp != null) {
            mBleScanImp.onScanStart();
        }
    }


    public void stopScan() {
        if (isScanning) {
            final BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
            if (scanner != null) {
                scanner.stopScan(mScanCallback);
                if (mBleScanImp != null) {
                    mBleScanImp.onScanStop();
                }
            }
            isScanning = false;
        }
    }

    public void setConnectTimeOut(int time) {
        CONNECT_TIMEOUT = time;
    }

    private void getBondedDevices() {
        if (mBleAdapter == null) {
            return;
        }
        Set<BluetoothDevice> devices = mBleAdapter.getBondedDevices();
        for (BluetoothDevice device : devices) {
            if (device == null) {
                continue;
            }
            if (isRepeatBinded(device)) {
                continue;
            }
            mBindedDevices.add(new BleDevice(device, BleDevice.BINDED));
        }
    }

    private boolean isRepeatBinded(BluetoothDevice device) {
        for (BleDevice bleDevice : mBindedDevices) {
            if (bleDevice == null || bleDevice.device == null) {
                return false;
            }
            String address = bleDevice.device.getAddress();
            if (TextUtils.isEmpty(address)) {
                return false;
            }
            return address.equals(device.getAddress());
        }
        return false;
    }

    private void getUnbondedDevices(List<ScanResult> results) {
        for (final ScanResult result : results) {
            if (result == null) {
                continue;
            }
            if (isBinded(result)) {
                continue;
            }
            if (isRepeatUnbind(result)) {
                continue;
            }
            BleDevice devices = new BleDevice(result);

            if (devices != null && !TextUtils.isEmpty(devices.name) && !isContains(devices)) {
                mUnbindDevices.add(devices);
            }
        }
    }

    private boolean isRepeatUnbind(ScanResult result) {
        if (result == null || result.getDevice() == null) {
            return false;
        }
        for (BleDevice bleDevice : mUnbindDevices) {
            if (bleDevice == null || bleDevice.device == null) {
                return false;
            }
            String address = bleDevice.device.getAddress();
            if (TextUtils.isEmpty(address)) {
                return false;
            }
            return address.equals(result.getDevice().getAddress());
        }
        return false;
    }

    private boolean isBinded(ScanResult result) {
        for (BleDevice bleDevice : mBindedDevices) {
            if (bleDevice == null || bleDevice.device == null) {
                return false;
            }
            String address = bleDevice.device.getAddress();
            if (TextUtils.isEmpty(address)) {
                return false;
            }
            if (address.equals(result.getDevice().getAddress())) {
                bleDevice.rssi = result.getRssi();
                return true;
            }
        }
        return false;
    }

    private boolean isContains(BleDevice result) {
        for (BleDevice device : mUnbindDevices) {
            if (TextUtils.equals(device.device.getAddress(), result.device.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public boolean getScanState() {
        return isScanning;
    }

}
