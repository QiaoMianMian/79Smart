package com.smartjewelry.blue105;


import com.smart.library.blue.BleApp;
import com.smart.library.blue.BleLogs;

public class App extends BleApp {

    @Override
    public void onCreate() {
        super.onCreate();
        bindBleService(getApplicationContext());
        BleLogs.setDebug(true);
        BleLogs.setTag("BleLogs");
    }
}
