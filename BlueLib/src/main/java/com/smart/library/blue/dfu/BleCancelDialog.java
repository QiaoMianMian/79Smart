package com.smart.library.blue.dfu;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class BleCancelDialog extends DialogFragment {
    private static final String TAG = BleCancelDialog.class.getSimpleName();

    private CancelFragmentListener mListener;

    public interface CancelFragmentListener {
        public void onCancelUpload();
    }

    public static BleCancelDialog getInstance() {
        return new BleCancelDialog();
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CancelFragmentListener) activity;
        } catch (final ClassCastException e) {
            Log.d(TAG, "The parent Activity must implement CancelFragmentListener interface");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Update Hardware")
                .setMessage("Are you sure you want to cancel the device upgrade?")
                .setCancelable(false)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int whichButton) {
                        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
                        final Intent pauseAction = new Intent(BleDfuService.BROADCAST_ACTION);
                        pauseAction.putExtra(BleDfuService.EXTRA_ACTION, BleDfuService.ACTION_ABORT);
                        manager.sendBroadcast(pauseAction);
                        if (mListener != null) {
                            mListener.onCancelUpload();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        dialog.cancel();
                    }
                }).create();
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        final LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getActivity());
        final Intent pauseAction = new Intent(BleDfuService.BROADCAST_ACTION);
        pauseAction.putExtra(BleDfuService.EXTRA_ACTION, BleDfuService.ACTION_RESUME);
        manager.sendBroadcast(pauseAction);
    }
}
