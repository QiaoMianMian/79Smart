package com.smart.library.blue;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

public class BleApp extends Application {

    public BleService bleService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BleService.LocalBinder binder = (BleService.LocalBinder) service;
            bleService = (BleService) binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bleService = null;
        }
    };

    /**
     * bind Ble Service
     */
    public void bindBleService(Context context) {
        int pid = android.os.Process.myPid();
        String proName = BleUtils.getProcessName(context, pid);
        if (TextUtils.equals(proName, getPackageName())) {
            Intent intent = new Intent(getApplicationContext(), BleService.class);
            getApplicationContext().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }
}
