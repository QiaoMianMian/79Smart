package com.smart.library.blue;

import android.util.Log;


public class BleLogs {
    public static boolean DEBUG = true;
    public static String TAG = BleLogs.class.getSimpleName();

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }


    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

}
