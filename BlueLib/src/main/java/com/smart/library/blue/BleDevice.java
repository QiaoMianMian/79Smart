package com.smart.library.blue;

import android.bluetooth.BluetoothDevice;

import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class BleDevice {
    public static final int UNBIND = 0;
    public static final int BINDED = 1;

    public BluetoothDevice device;
    public String name;
    public int rssi;
    public int bindState;
    public boolean isBonded;

    public BleDevice(ScanResult scanResult) {
        this.device = scanResult.getDevice();
        this.name = scanResult.getScanRecord() != null ? scanResult.getScanRecord().getDeviceName() : null;
        this.rssi = scanResult.getRssi();
        isBonded = false;
        bindState = UNBIND;
    }

    public BleDevice(BluetoothDevice device, int state) {
        this.device = device;
        this.name = device.getName();
        isBonded = true;
        this.bindState = state;
    }
}
