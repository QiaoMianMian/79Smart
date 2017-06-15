package com.smart.library.blue.dfu;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import no.nordicsemi.android.dfu.DfuProgressListenerAdapter;


public class BleDfuListener extends DfuProgressListenerAdapter {
    private String TAG = BleDfuListener.class.getSimpleName();
    private ProgressBar pb;
    private Button upload;
    private Context mContext;

    public BleDfuListener(Context context, ProgressBar pb, Button upload) {
        super();
        this.mContext = context;
        this.pb = pb;
        this.upload = upload;
    }

    @Override
    public void onDeviceConnecting(String deviceAddress) {
        super.onDeviceConnecting(deviceAddress);
        Log.i(TAG, "onDeviceConnecting:" + deviceAddress);
        pb.setIndeterminate(true);
        upload.setText("CANCEL");
    }

    @Override
    public void onDeviceConnected(String deviceAddress) {
        super.onDeviceConnected(deviceAddress);
        Log.i(TAG, "onDeviceConnected:" + deviceAddress);
    }

    @Override
    public void onDfuProcessStarting(String deviceAddress) {
        super.onDfuProcessStarting(deviceAddress);
        Log.i(TAG, "onDfuProcessStarting:" + deviceAddress);
        pb.setProgress(0);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDfuProcessStarted(String deviceAddress) {
        super.onDfuProcessStarted(deviceAddress);
        Log.i(TAG, "onDfuProcessStarted:" + deviceAddress);
    }

    @Override
    public void onEnablingDfuMode(String deviceAddress) {
        super.onEnablingDfuMode(deviceAddress);
        Log.i(TAG, "onEnablingDfuMode:" + deviceAddress);
        pb.setIndeterminate(true);
    }

    @Override
    public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
        super.onProgressChanged(deviceAddress, percent, speed, avgSpeed, currentPart, partsTotal);
        Log.i(TAG, "onProgressChanged:" + percent);
        pb.setIndeterminate(false);
        pb.setProgress(percent);
    }

    @Override
    public void onFirmwareValidating(String deviceAddress) {
        super.onFirmwareValidating(deviceAddress);
        Log.i(TAG, "onFirmwareValidating:" + deviceAddress);
        pb.setIndeterminate(true);
    }

    @Override
    public void onDeviceDisconnecting(String deviceAddress) {
        super.onDeviceDisconnecting(deviceAddress);
        Log.i(TAG, "onDeviceDisconnecting:" + deviceAddress);
        pb.setIndeterminate(true);
    }

    @Override
    public void onDeviceDisconnected(String deviceAddress) {
        super.onDeviceDisconnected(deviceAddress);
        Log.i(TAG, "onDeviceDisconnected:" + deviceAddress);
    }

    @Override
    public void onDfuCompleted(String deviceAddress) {
        super.onDfuCompleted(deviceAddress);
        Log.i(TAG, "onDfuCompleted:" + deviceAddress);
        pb.setVisibility(View.GONE);
        upload.setText("SUCCESS");
    }

    @Override
    public void onDfuAborted(String deviceAddress) {
        super.onDfuAborted(deviceAddress);
        Log.i(TAG, "onDfuAborted:" + deviceAddress);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onError(String deviceAddress, int error, int errorType, String message) {
        super.onError(deviceAddress, error, errorType, message);
        Log.e(TAG, "onError:" + deviceAddress);
        pb.setVisibility(View.GONE);
        upload.setText("FAILED");
    }
}
