package com.smart.library.blue;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;

public class BleService extends Service {
    private final IBinder mIBinder = new LocalBinder();

    public BluetoothGatt gatt;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        public BleService getService() {
            return BleService.this;
        }
    }

    public boolean connect(String address) {
        if (TextUtils.isEmpty(address)) {
            return false;
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            BleLogs.e("adapter == null");
            return false;
        }
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            BleLogs.e("device == null");
            return false;
        }
        if (gatt == null) {
            gatt = device.connectGatt(this, false, new BleCallback(BleService.this));
        }
        return gatt.connect();
    }

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect() {
        if (mBluetoothAdapter == null || gatt == null) {
            BleLogs.e("BluetoothAdapter not initialized");
            return;
        }
        gatt.disconnect();
    }

}
