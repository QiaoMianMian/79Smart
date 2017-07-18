package com.smartjewelry.blue105;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.library.blue.BleCode;
import com.smart.library.blue.BleContants;
import com.smart.library.blue.BleImp;
import com.smart.library.blue.BleReceiver;
import com.smart.library.blue.BleSend;
import com.smart.library.blue.BleService;
import com.smart.library.blue.dfu.BleCancelDialog;
import com.smart.library.blue.dfu.BleDfuListener;
import com.smart.library.blue.dfu.BleDfuService;
import com.smart.library.db.DbUtils;
import com.smart.library.model.SleepModel;

import java.util.List;

import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

public class BleControlActivity extends AppCompatActivity implements View.OnClickListener, BleImp, BleCancelDialog.CancelFragmentListener {

    String TAG = BleControlActivity.class.getSimpleName();

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private BleReceiver mReceiver;

    private TextView mBlueName;

    private TextView mBlueState;

    private BleService mBleService;

    private Button btn_upgrade;

    private ProgressBar pb_dfu_upload;

    private BleDfuListener mDfuListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_control);
        initData();
        initViews();

        mReceiver = new BleReceiver(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DfuServiceListenerHelper.registerProgressListener(this, mDfuListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        DfuServiceListenerHelper.unregisterProgressListener(this, mDfuListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void initData() {
        String mDeviceAddress = getIntent().getStringExtra(EXTRAS_DEVICE_ADDRESS);
        String mDeviceName = getIntent().getStringExtra(EXTRAS_DEVICE_NAME);
        Log.i(TAG, "Connect:" + mDeviceName + "[" + mDeviceAddress + "]");
        mBleService = ((App) getApplication()).bleService;
        if (mBleService != null && mBleService.connect(mDeviceAddress)) {
            BleContants.BLE_CONNECTED_NAME = mDeviceName;
            BleContants.BLE_CONNECTED_ADDRESS = mDeviceAddress;
        }
    }

    private void initViews() {
        mBlueName = (TextView) findViewById(R.id.blue_name);
        mBlueState = (TextView) findViewById(R.id.blue_state);

        btn_upgrade = (Button) findViewById(R.id.btn_upgrade);

        pb_dfu_upload = (ProgressBar) findViewById(R.id.dfu_upload_pb);
        mDfuListener = new BleDfuListener(this, pb_dfu_upload, btn_upgrade);
    }

    /**
     * hardware upgrade
     */
    private void upgrade() {
        if (isServiceRunning(this, BleDfuService.class.getName())) {
            showUploadCancelDialog();
        }
        if (mBleService != null && mBleService.gatt != null) {
            BluetoothDevice device = mBleService.gatt.getDevice();
            if (device != null) {
                DfuServiceInitiator starter = new DfuServiceInitiator(device.getAddress()).setDeviceName(device.getName()).setKeepBond(false);
                starter.setZip(R.raw.v105_207_20170616);
                starter.start(this, BleDfuService.class);
            } else {
                Log.e(TAG, "device = null");
            }
        }
    }

    private void showUploadCancelDialog() {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
        final Intent pauseAction = new Intent(BleDfuService.BROADCAST_ACTION);
        pauseAction.putExtra(BleDfuService.EXTRA_ACTION, BleDfuService.ACTION_PAUSE);
        manager.sendBroadcast(pauseAction);

        BleCancelDialog fragment = BleCancelDialog.getInstance();
        fragment.show(getSupportFragmentManager(), TAG);
    }

    /**
     * 服务是否运行
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_device_type:
                BleSend.getInstance().sendDeviceType(this);//Device Type
                break;
            case R.id.btn_device_code:
                BleSend.getInstance().sendDeviceCode(this);//Device Code
                break;
            case R.id.btn_hardware_version:
                BleSend.getInstance().sendDeviceVersion(this);//Device Version
                break;
            case R.id.btn_hardware_battery:
                BleSend.getInstance().sendDeviceBattery(this);//Device Battery
                break;
            case R.id.btn_current_step:
                BleSend.getInstance().sendCurrentSteps(this);//Current Step
                break;
            case R.id.btn_step_sync:
                BleSend.getInstance().synchStep(this);//Synch History Step
                break;
            case R.id.btn_history_step:                 //Get History Step
                String stepTxt = "";
                List<String> stepPeriods = DbUtils.getStepPeriodDate(this);
                for (String date : stepPeriods) {
                    String steps = DbUtils.getOneDaySteps(this, date);
                    stepTxt += date + ":[" + steps + "]\n";
                }
                Log.i(TAG, stepTxt);
                setText(stepTxt);
                break;
            case R.id.btn_current_sleep:
                BleSend.getInstance().sendCurrentSleep(this); //Synch Current Sleep
                break;
            case R.id.btn_sleep_sync:
                BleSend.getInstance().synchSleepSum(this);//Synch History Sleep
                break;
            case R.id.btn_history_sleep:            //Get History Sleep
                String sleepTxt = "";
                List<String> sleepPeriods = DbUtils.getSleepPeriodDate(this);
                for (String date : sleepPeriods) {
                    SleepModel model = DbUtils.getSleepModel(this, date);
                    sleepTxt += date + ":[" + model.getSleepActive() + "," + model.getSleepLight() + "," + model.getSleepDeep()
                            + "," + model.getSleepStartTime() + "," + model.getSleepEndTime() + "]\n";
                }
                Log.i(TAG, sleepTxt);
                setText(sleepTxt);
                break;

            case R.id.btn_upgrade:
                if (BleContants.STATE_BLE_CONNECTED) {
                    upgrade();
                } else {
                    Toast.makeText(this, "BLE_DISCONNECT", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reset:
                if (BleContants.STATE_BLE_CONNECTED) {
                    resetDialog();
                }
                break;
        }
    }

    private void setText(String result) {
        TextView button = ((TextView) findViewById(R.id.tv_data));
        button.setText(result);
    }

    @Override
    public void onSuccess(BleCode code, Object result) {
        Log.i(TAG, "code=" + code + ",result=" + result);
        if (code == BleCode.CONNECTED) {
            mBlueName.setText(BleContants.BLE_CONNECTED_NAME);
            mBlueState.setText("CONNECTED");
        } else if (code == BleCode.DISCONNECTED) {
            mBlueName.setText("");
            mBlueState.setText("DISCONNECTED");
        } else if (code == BleCode.DEVICETYPE) {
            setText(code + ", " + result);
        } else if (code == BleCode.DEVICECODE) {
            setText(code + ", " + result);
        } else if (code == BleCode.CURRENTSTEP) {
            setText(code + ", " + result);
        } else if (code == BleCode.HISTORYSTEPS) {
            setText(code + ", " + result);
            findViewById(R.id.btn_history_step).setClickable(true);
            if (TextUtils.equals((String) result, "Completed")) {
//                BleSend.getInstance().clearStepFlash(this);
                setText("Step Sync Completed");
            }
        } else if (code == BleCode.CURRENTSLEEP) { //[active,light,deep,sleep start,sleep end]
            setText(code + ", " + result);
        } else if (code == BleCode.HISTORYSLEEP) {
            setText(code + ", " + result);
            findViewById(R.id.btn_history_sleep).setClickable(true);
            if (TextUtils.equals((String) result, "Completed")) {
//                BleSend.getInstance().clearSleepFlash(this);
                setText("Sleep Sync Completed");
            }
        } else if (code == BleCode.DEVICEVERSION) {
            setText(code + ", " + result);
        } else if (code == BleCode.DEVICEBATTERY) {
            setText(code + ", " + result);
        }
    }

    @Override
    public void onFail(String error) {

    }

    @Override
    public void onCancelUpload() {
        pb_dfu_upload.setIndeterminate(true);
        btn_upgrade.setText(R.string.software_upgrade);
    }

    private void resetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("Reset"); //设置标题
        builder.setMessage("Reset Device Data?"); //设置内容
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                BleSend.getInstance().sendReset(BleControlActivity.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
