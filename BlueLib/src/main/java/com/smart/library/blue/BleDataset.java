package com.smart.library.blue;

import android.text.TextUtils;


public class BleDataset {

    /**
     * Device Type
     */
    public static byte[] setDeviceType() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x02;
        return data;
    }

    /**
     * Device Code
     */
    public static byte[] setDeviceCode() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x03;
        return data;
    }

    /**
     * Current Steps
     */
    public static byte[] setCurrentSteps() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x06;
        return data;
    }

    /**
     * Clear Current Steps
     */
    public static byte[] clearCurrentSteps() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x06;
        data[2] = 0x01;
        return data;
    }

    /**
     * Get Steps Index
     */
    public static byte[] synchStep() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x09;
        return data;
    }

    /**
     * Synch Steps
     */
    public static byte[] synchStep(int index) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0a;
        data[2] = 0x00;
        data[3] = (byte) index;
        return data;
    }

    /**
     * Synch Sleep Index
     */
    public static byte[] synchSleepSum() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x26;
        return data;
    }

    /**
     * Synch Sleep
     */
    public static byte[] synchSleepSum(int index) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x27;
        data[2] = 0x00;
        data[3] = (byte) index;
        return data;
    }

    /**
     * Synch Current Sleep
     */
    public static byte[] sendCurrentSleep() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x28;
        return data;
    }

    /**
     * On/Off Tel Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifyTelSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x01;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off Sms Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifySmsSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x02;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off Email Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifyEmailSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x03;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off Wxin Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifyWxinSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x0A;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off Wbo Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifyWboSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x0B;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off NoReply Tel Notify
     *
     * @param state
     * @param shock
     */
    public static byte[] setNotifyTelNoAnswerSwitch(int state, int shock) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x0C;
        data[4] = (byte) (state & 0xFF);
        data[5] = (byte) (shock & 0xFF);
        return data;
    }

    /**
     * On/Off Lost Notify
     *
     * @param state
     */
    public static byte[] setNotifyLost(int state) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0d;
        data[2] = 0x00;
        data[3] = 0x07;
        data[4] = (byte) (state & 0xFF);
        return data;
    }

    /**
     * Tel Notify
     *
     * @param type
     */
    public static byte[] setNotifyTel(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x01;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Sms Notify
     *
     * @param type
     */
    public static byte[] setNotifySms(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x02;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Email Notify
     *
     * @param type
     */
    public static byte[] setNotifyEmail(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x03;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Wxin Notify
     *
     * @param type
     */
    public static byte[] setNotifyWxin(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x05;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Wbo Notify
     *
     * @param type
     */
    public static byte[] setNotifyWbo(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x06;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Noreply Tel Notify
     *
     * @param type
     */
    public static byte[] setNotifyTelNoReply(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x07;
        data[4] = (byte) type;
        return data;
    }

    /**
     * Lost Notify
     *
     * @param type
     */
    public static byte[] setNotifyLost1(int type) {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x0e;
        data[2] = 0x00;
        data[3] = 0x04;
        data[4] = (byte) (type & 0xFF);
        return data;
    }

    /**
     * Device Version
     */
    public static byte[] setDeviceVersion() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x14;
        return data;
    }

    /**
     * Device Battery
     */
    public static byte[] setDeviceBattery() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x15;
        return data;
    }

    /**
     * Clear Step Flash
     */
    public static byte[] clearStepFlash() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x1B;
        data[2] = 0x00;
        data[3] = 0x01;
        return data;
    }

    /**
     * Clear Sleep Flash
     */
    public static byte[] clearSleepFlash() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x1B;
        data[2] = 0x00;
        data[3] = 0x02;
        return data;
    }


    /**
     * Get Device Time
     */
    public static byte[] getDeviceTime() {
        byte[] data = new byte[20];
        data[0] = 0x5A;
        data[1] = 0x1D;
        return data;
    }

    /**
     * Set Device Time
     *
     * @param date 2016-05-23 17:33:22
     */
    public static byte[] setDeviceTime(String date) {
        int year, month, day, hour, minute, second;
        byte[] data = new byte[20];
        if (TextUtils.isEmpty(date)) {
            return data;
        }
        String[] a = date.split(" ");
        if (a == null && a.length < 2) {
            return data;
        }
        String[] b = a[0].split("-");
        String[] c = a[1].split(":");
        if ((b == null && b.length < 3) || (c == null && c.length < 3)) {
            return data;
        }
        year = (Integer.parseInt(b[0]) - 2000);
        month = Integer.parseInt(b[1]);
        day = Integer.parseInt(b[2]);
        hour = Integer.parseInt(c[0]);
        minute = Integer.parseInt(c[1]);
        second = Integer.parseInt(c[2]);

        data[0] = 0x5A;
        data[1] = 0x1E;
        data[2] = 0x00;
        data[3] = (byte) year;
        data[4] = (byte) month;
        data[5] = (byte) day;
        data[6] = (byte) hour;
        data[7] = (byte) minute;
        data[8] = (byte) second;
        int count = BleString.bytesCheckAnd(data);
        data[18] = (byte) ((count >> 8) & 0xFF);
        data[19] = (byte) (count & 0xFF);
        return data;
    }
}
