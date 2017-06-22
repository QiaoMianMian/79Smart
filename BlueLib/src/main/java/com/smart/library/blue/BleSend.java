package com.smart.library.blue;

import android.content.Context;

public class BleSend {

    public static BleSend instance;

    public static BleSend getInstance() {
        if (instance == null) {
            instance = new BleSend();
        }
        return instance;
    }

    /**
     * Device Type
     *
     * @param context
     */
    public boolean sendDeviceType(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setDeviceType());
    }

    /**
     * Device Code
     *
     * @param context
     */
    public boolean sendDeviceCode(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setDeviceCode());
    }

    /**
     * Current Steps
     *
     * @param context
     */
    public boolean sendCurrentSteps(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setCurrentSteps());
    }

    /**
     * Clear Current Steps
     */
    public static boolean clearCurrentSteps(Context context) {
        return BleUtils.writeToBle(context, BleDataset.clearCurrentSteps());
    }

    /**
     * Get Steps Index
     *
     * @param context
     */
    public boolean synchStep(Context context) {
        return BleUtils.writeToBle(context, BleDataset.synchStep());
    }

    /**
     * Synch Steps
     *
     * @param context
     * @param index
     */
    public boolean synchStep(Context context, int index) {
        return BleUtils.writeToBle(context, BleDataset.synchStep(index));
    }

    /**
     * Get Sleep Index
     *
     * @param context
     */
    public static boolean synchSleepSum(Context context) {
        return BleUtils.writeToBle(context, BleDataset.synchSleepSum());
    }

    /**
     * Synch Sleep
     *
     * @param context
     */
    public static boolean synchSleepSum(Context context, int day) {
        return BleUtils.writeToBle(context, BleDataset.synchSleepSum(day));
    }

    /**
     * Synch Current Sleep
     *
     * @param context
     */
    public static boolean sendCurrentSleep(Context context) {
        return BleUtils.writeToBle(context, BleDataset.sendCurrentSleep());
    }

    /**
     * The Switch of Tel Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifyTelSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyTelSwitch(state, shock));
    }

    /**
     * The Switch of Sms Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifySmsSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifySmsSwitch(state, shock));
    }

    /**
     * The Switch of Email Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifyEmailSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyEmailSwitch(state, shock));
    }

    /**
     * The Switch of Wxin Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifyWxinSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyWxinSwitch(state, shock));
    }

    /**
     * The Switch of Wbo Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifyWboSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyWboSwitch(state, shock));
    }

    /**
     * The Switch of Noreply Tel Notify
     *
     * @param context
     * @param state
     * @param shock
     */
    public boolean sendNotifyTelNoAnswerSwitch(Context context, int state, int shock) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyTelNoAnswerSwitch(state, shock));
    }

    /**
     * The Switch of Lost Notify
     *
     * @param context
     * @param state
     */
    public boolean sendNotifyLost(Context context, int state) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyLost(state));
    }

    /**
     * Tel Notify
     *
     * @param context
     * @param type    0x00：New    0x01：Reply/Hang Up
     */
    public boolean sendNotifyTel(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyTel(type));
    }

    /**
     * Tel Notify
     *
     * @param context
     * @param type    0x00：New    0x01：Viewed
     */
    public boolean sendNotifySms(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifySms(type));
    }

    /**
     * Email Notify
     *
     * @param context
     * @param type    0x00：New    0x01：Viewed
     */
    public boolean sendNotifyEmail(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyEmail(type));
    }

    /**
     * Wxin Notify
     *
     * @param context
     * @param type
     */
    public boolean sendNotifyWxin(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyWxin(type));
    }

    /**
     * Wbo Notify
     *
     * @param context
     * @param type
     */
    public boolean sendNotifyWbo(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyWbo(type));
    }

    /**
     * No Reply Notify
     *
     * @param context
     * @param type
     */
    public boolean sendNotifyTelNoReply(Context context, int type) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyTelNoReply(type));
    }

    /**
     * Lost Notify
     *
     * @param context
     * @param state
     */
    public boolean sendNotifyLost1(Context context, int state) {
        return BleUtils.writeToBle(context, BleDataset.setNotifyLost1(state));
    }

    /**
     * Device Battery
     *
     * @param context
     */
    public boolean sendDeviceBattery(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setDeviceBattery());
    }

    /**
     * Device Version
     *
     * @param context
     */
    public boolean sendDeviceVersion(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setDeviceVersion());
    }

    /**
     * Reset
     *
     * @param context
     */
    public boolean sendReset(Context context) {
        return BleUtils.writeToBle(context, BleDataset.setReset());
    }

    /**
     * Clear Step Flash
     */
    public boolean clearStepFlash(Context context) {
        return BleUtils.writeToBle(context, BleDataset.clearStepFlash());
    }

    /**
     * Clear Sleep Flash
     */
    public boolean clearSleepFlash(Context context) {
        return BleUtils.writeToBle(context, BleDataset.clearSleepFlash());
    }


    /**
     * Get Device Time
     *
     * @param context
     */
    public boolean sendGetDeviceTime(Context context) {
        return BleUtils.writeToBle(context, BleDataset.getDeviceTime());
    }

    /**
     * Set Device Time
     *
     * @param context
     * @param date    2016-05-23 17:33:22
     */
    public boolean sendSetDeviceTime(Context context, String date) {
        return BleUtils.writeToBle(context, BleDataset.setDeviceTime(date));
    }

}
