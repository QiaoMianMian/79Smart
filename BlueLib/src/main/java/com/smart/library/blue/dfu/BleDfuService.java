
package com.smart.library.blue.dfu;

import android.app.Activity;

import com.smart.library.BleActivity;

import no.nordicsemi.android.dfu.DfuBaseService;

public class BleDfuService extends DfuBaseService {

    @Override
    protected Class<? extends Activity> getNotificationTarget() {
        return BleActivity.class;
    }

}
