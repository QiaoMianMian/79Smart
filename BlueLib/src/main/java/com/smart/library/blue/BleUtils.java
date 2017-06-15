package com.smart.library.blue;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class BleUtils {

    /**
     * BLE Support
     */
    public static boolean isBleDevice(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * Check And Open Blue
     */
    public static boolean isOpenBlue(Context context) {
        BluetoothAdapter mBleAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBleAdapter == null) {
            BleLogs.e("This device does not have Bluetooth function");
            return false;
        }
        if (!isBleDevice(context)) {
            BleLogs.e("This device does not support Bluetooth 4.0 features");
            return false;
        }
        if (mBleAdapter.isEnabled()) {
            return true;
        }
        return false;
    }


    /**
     * Write Data To Ble
     */
    public static boolean writeToBle(Context context, byte[] bytes) {
        if (context == null) {
            BleLogs.e("context == null");
            return false;
        }
        if (context.getApplicationContext() == null) {
            BleLogs.e("context.getApplicationContext() == null");
            return false;
        }
        if (((BleApp) context.getApplicationContext()).bleService == null) {
            BleLogs.e("bleService == null");
            return false;
        }
        BluetoothGatt gatt = ((BleApp) context.getApplicationContext()).bleService.gatt;
        if (gatt == null) {
            BleLogs.e("gatt == null");
            return false;
        }
        BluetoothGattService gattService = gatt.getService(BleContants.UUID_NUS_SERVICE);
        if (gattService == null) {
            BleLogs.e("gattService == null");
            return false;
        }
        BluetoothGattCharacteristic gattCharacteristic = gattService.getCharacteristic(BleContants.UUID_NUS_CHARACTER);
        if (gattCharacteristic == null) {
            BleLogs.e("gattCharacteristic == null");
            return false;
        }
        gattCharacteristic.setValue(bytes);
        return gatt.writeCharacteristic(gattCharacteristic);
    }

    /**
     * Read Data From Ble
     */
    public static boolean readFromBle(BluetoothGatt gatt) {
        if (gatt == null) {
            BleLogs.e("gatt == null");
            return false;
        }
        BluetoothGattService gattService = gatt.getService(BleContants.UUID_NUS_SERVICE);
        if (gattService == null) {
            BleLogs.e("gattService == null");
            return false;
        }
        BluetoothGattCharacteristic gattCharacteristic = gattService.getCharacteristic(BleContants.UUID_NUS_CHARACTER);
        if (gattCharacteristic == null) {
            BleLogs.e("gattCharacteristic == null");
            return false;
        }
        return gatt.readCharacteristic(gattCharacteristic);
    }

    public static boolean removeBondMethod(BleDevice d) {
        try {
            Method removeBondMethod = d.device.getClass().getMethod("removeBond");
            Boolean returnValue = (Boolean) removeBondMethod.invoke(d.device);
            return returnValue;
        } catch (NoSuchMethodException e) {
            BleLogs.e(e.toString());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            BleLogs.e(e.toString());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            BleLogs.e(e.toString());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Process Name
     */
    public static String getProcessName(Context context, int pid) {
        if (context == null) {
            return null;
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
