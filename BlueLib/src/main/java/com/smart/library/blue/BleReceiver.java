package com.smart.library.blue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.smart.library.db.DbUtils;
import com.smart.library.model.SleepModel;
import com.smart.library.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;


public class BleReceiver extends BroadcastReceiver {

    String TAG = BleReceiver.class.getSimpleName();

    private BleImp mBleImp;

    private static int mStepIndex = 0;  //Step Index
    private static long mStepStartTime = 0;  //Step Start Time
    private static int mStepCount = 0;   // Total number of Step
    private static int mStepPackage = 0;  // Package number of Step
    private static int mStepSynch = 0;  //Whether to synchronize time

    private static int mSleepSumIndex = 0;  //Sleep Index
    private static long mSleepSumStartTime = 0;
    private static long mSleepSumEndTime = 0;
    private static long mSleepSumSynch = 0; //Whether to synchronize time

    private static String mSleepCurrentStartTime;
    private static String mSleepCurrentEndTime;

    public BleReceiver(Context context, BleImp imp) {
        this.mBleImp = imp;
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(BleContants.ACTION_BLE_CONNECTED);
        mFilter.addAction(BleContants.ACTION_BLE_DISCONNECT);
        mFilter.addAction(BleContants.ACTION_BLE_DEVICE_TYPE);
        mFilter.addAction(BleContants.ACTION_BLE_DEVICE_CODE);
        mFilter.addAction(BleContants.ACTION_BLE_CURRENT_STEPS);
        mFilter.addAction(BleContants.ACTION_BLE_HISTORY_STEPS_INDEX);
        mFilter.addAction(BleContants.ACTION_BLE_HISTORY_STEPS_DATA);
        mFilter.addAction(BleContants.ACTION_BLE_CURRENT_SLEEP);
        mFilter.addAction(BleContants.ACTION_BLE_HISTORY_SLEEPSUM_INDEX);
        mFilter.addAction(BleContants.ACTION_BLE_HISTORY_SLEEPSUM_DATA);
        mFilter.addAction(BleContants.ACTION_BLE_VERSION);
        mFilter.addAction(BleContants.ACTION_BLE_BATTERY);
        mFilter.addAction(BleContants.ACTION_BLE_FLASH_CLEAR);
        mFilter.addAction(BleContants.ACTION_BLE_SLEEP_SWITCH);
        context.registerReceiver(this, mFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ArrayList<Integer> data = intent.getIntegerArrayListExtra("data");
        BleLogs.i(TAG, action + ":" + (data != null ? data.toString().replace(", ", "] [") : null));
        if (TextUtils.equals(BleContants.ACTION_BLE_CONNECTED, action)) {
            if (mBleImp != null) {
                mBleImp.onSuccess(BleCode.CONNECTED, "CONNECTED");
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_DISCONNECT, action)) {
            if (mBleImp != null) {
                mBleImp.onSuccess(BleCode.DISCONNECTED, "DISCONNECTED");
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_DEVICE_TYPE, action)) {
            if (data != null && data.size() > 7) {
                char d3 = BleString.Integer2Ascii(data.get(3));
                char d4 = BleString.Integer2Ascii(data.get(4));
                char d5 = BleString.Integer2Ascii(data.get(5));
                String type = "" + d3 + d4 + d5;
                BleLogs.i(TAG, "type:" + type);
                if (mBleImp != null) {
                    mBleImp.onSuccess(BleCode.DEVICETYPE, type);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_DEVICE_CODE, action)) {
            if (data != null && data.size() > 18) {
                String d3 = String.valueOf(BleString.Integer2Ascii(data.get(3)));
                String d4 = String.valueOf(BleString.Integer2Ascii(data.get(4)));
                String d5 = String.valueOf(BleString.Integer2Ascii(data.get(5)));
                String d6 = String.valueOf(BleString.Integer2Ascii(data.get(6)));
                String d7 = String.valueOf(BleString.Integer2Ascii(data.get(7)));
                String d8 = String.valueOf(BleString.Integer2Ascii(data.get(8)));
                String d9 = String.valueOf(BleString.Integer2Ascii(data.get(9)));
                String d10 = String.valueOf(BleString.Integer2Ascii(data.get(10)));
                String d11 = String.valueOf(BleString.Integer2Ascii(data.get(11)));
                String d12 = String.valueOf(BleString.Integer2Ascii(data.get(12)));
                String d13 = String.valueOf(BleString.Integer2Ascii(data.get(13)));
                String d14 = String.valueOf(BleString.Integer2Ascii(data.get(14)));
                String d15 = String.valueOf(BleString.Integer2Ascii(data.get(15)));
                String d16 = String.valueOf(BleString.Integer2Ascii(data.get(16)));
                String d17 = String.valueOf(BleString.Integer2Ascii(data.get(17)));
                String d18 = String.valueOf(BleString.Integer2Ascii(data.get(18)));
                String sn = d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + d12 + d13 + d14 + d15 + d16 + d17 + d18;
                BleLogs.i(TAG, "Code:" + sn);
                if (mBleImp != null) {
                    mBleImp.onSuccess(BleCode.DEVICECODE, sn);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_CURRENT_STEPS, action)) {
            if (data != null && data.size() > 9) {
                int d2 = data.get(2);
                if (d2 == 0) {
                    int d3 = data.get(3);
                    int d4 = data.get(4);
                    int d5 = data.get(5);
                    int d6 = data.get(6);
                    int d7 = data.get(7);
                    int d8 = data.get(8);
                    int d9 = data.get(9);
                    int step = d6 | (d5 << 8) | (d4 << 16) | (d3 << 24);//ble
                    int duration = (d9 + (d8 * 60) + (d7 * 60 * 60));

                    if (mBleImp != null) {
                        mBleImp.onSuccess(BleCode.CURRENTSTEP, step + ", " + duration + "s");
                    }
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_HISTORY_STEPS_INDEX, action)) {
            if (data != null && data.size() > 3) {
                mStepIndex = data.get(3);
                if (mStepIndex > 0) {
                    BleLogs.i(TAG, "mStepIndex:" + mStepIndex);
                    BleSend.getInstance().synchStep(context, 1);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_HISTORY_STEPS_DATA, action)) {
            if (data != null && data.size() > 19) {
                int number = data.get(2);
                int index = data.get(3);
                if (!BleString.isCheckAnd(data)) {
                    BleSend.getInstance().synchStep(context, index);
                    BleLogs.e(TAG, "validation failed");
                    return;
                }
                if (number == 0) {  // first data
                    mStepStartTime = DateUtils.minuteString2Long(DateUtils.combinMinuteString(
                            (2000 + data.get(4)), data.get(5), data.get(6), data.get(7), data.get(8))) / 1000;
                    mStepCount = BleString.shift(data.get(9), data.get(10)) / 2;
                    mStepPackage = BleString.shift(data.get(11), data.get(12));
                    mStepSynch = data.get(13);
                    BleLogs.i(TAG, DateUtils.long2MinuteString(mStepStartTime) + ", "
                            + DateUtils.long2MinuteString(mStepStartTime + mStepCount * 10 * 60) + ", "
                            + mStepCount + ", " + mStepPackage + ", " + mStepSynch);
                } else {
                    if (mStepSynch == 0) {  // time no synchronization

                    } else {   //time synchronization
                        int[] d = {
                                BleString.shift(data.get(4), data.get(5)),
                                BleString.shift(data.get(8), data.get(9)),
                                BleString.shift(data.get(12), data.get(13))
                        };
                        int[] t = {
                                BleString.duration(data.get(6), data.get(7)),
                                BleString.duration(data.get(10), data.get(11)),
                                BleString.duration(data.get(14), data.get(15))
                        };
                        int count = 3;
                        if (number == mStepPackage && mStepCount % 3 != 0) {
                            count = mStepCount % 3;
                        }
                        for (int i = 1; i <= count; i++) {
                            /**
                             * (i - 1)  a pieces of data
                             * (number - 1)  a number of packages(starting from the second pack)
                             *  3  a pack of 3 data
                             * 10  a data for 10 minutes
                             * 60  a hour = 60 minutes
                             */
                            long time = mStepStartTime + ((i - 1) + (number - 1) * 3) * 10 * 60;
                            //ignore error date data
                            long currentLong = new Date().getTime() / 1000;
                            long startLong = DateUtils.dayString2Long("2017-01-01") / 1000;
                            if (time < startLong || time > currentLong) {
                                BleLogs.e(TAG, "Invalid step:" + DateUtils.long2SecondString(time));
                                continue;
                            }

                            String timer = DateUtils.long2TenString(time);
                            int steps = d[i - 1];
                            int duration = t[i - 1];
                            BleLogs.i(TAG, number + ", " + mStepPackage + ", " + mStepCount + ", " + timer + ", " + steps + ", " + duration);
                            DbUtils.replaceStep(context, String.valueOf(steps), timer, duration);
                        }
                    }
                }
                BleLogs.i(TAG, index + ", " + mStepIndex + ", " + number + ", " + mStepSynch + ", " + mStepPackage);
                /**
                 * (number == 0 && mStepSynch == 0)   Not synchronized time the first one send cmd
                 * (number > 0 && number == mStepPackage)  synchronization time the last one send cmd
                 * (mStepPackage == 0 && mStepSynch == 1)  have Index  but not have package
                 */
                if (index < mStepIndex && ((number == 0 && mStepSynch == 0) || (number > 0 && number == mStepPackage)
                        || (mStepPackage == 0 && mStepSynch == 1))) {
                    BleSend.getInstance().synchStep(context, (index + 1));
                }
                //synchronized completed
                if (mBleImp != null) {
                    if ((index == mStepIndex) && (number == mStepPackage)) {
                        mBleImp.onSuccess(BleCode.HISTORYSTEPS, "Completed");
                    } else {
                        mBleImp.onSuccess(BleCode.HISTORYSTEPS, "Synchronizing");
                    }
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_CURRENT_SLEEP, action)) {
            if (data != null && data.size() > 19) {
                int number = data.get(2);
                if (!BleString.isCheckAnd(data)) {
                    BleSend.sendCurrentSleep(context);
                    return;
                }
                if (number == 0) {
                    mSleepCurrentStartTime = DateUtils.combinMinuteString((2000 + data.get(4)), data.get(5), data.get(6), data.get(7), data.get(8));
                    mSleepCurrentEndTime = DateUtils.combinMinuteString((2000 + data.get(9)), data.get(10), data.get(11), data.get(12), data.get(13));
                } else {
                    int active = BleString.shift(data.get(4), data.get(5));
                    int light = BleString.shift(data.get(6), data.get(7));
                    int deep = BleString.shift(data.get(8), data.get(9));
                    if (mBleImp != null) {
                        SleepModel model = new SleepModel();
                        model.setSleepActive(active);
                        model.setSleepLight(light);
                        model.setSleepDeep(deep);
                        model.setSleepStartTime(mSleepCurrentStartTime);
                        model.setSleepEndTime(mSleepCurrentEndTime);
                        mBleImp.onSuccess(BleCode.CURRENTSLEEP, model);
                    }
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_HISTORY_SLEEPSUM_INDEX, action)) {
            if (data != null && data.size() > 3) {
                mSleepSumIndex = data.get(3);
                if (mSleepSumIndex > 0) {
                    BleLogs.i(TAG, "mSleepSumIndex:" + mSleepSumIndex);
                    DbUtils.delAllSleep(context);
                    BleSend.getInstance().synchSleepSum(context, 1);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_HISTORY_SLEEPSUM_DATA, action)) {
            if (data != null && data.size() > 19) {
                int number = data.get(2);
                int index = data.get(3);
                if (!BleString.isCheckAnd(data)) {
                    BleSend.synchSleepSum(context, index);
                    BleLogs.e(TAG, "validation failed");
                    return;
                }
                if (number == 0) {
                    mSleepSumStartTime = DateUtils.minuteString2Long(
                            DateUtils.combinMinuteString((2000 + data.get(4)), data.get(5), data.get(6), data.get(7), data.get(8))) / 1000;
                    mSleepSumEndTime = DateUtils.minuteString2Long(
                            DateUtils.combinMinuteString((2000 + data.get(9)), data.get(10), data.get(11), data.get(12), data.get(13))) / 1000;
                    mSleepSumSynch = data.get(14);
                } else {
                    if (mSleepSumSynch == 0) {  // time no synchronization

                    } else {    //time synchronization
                        String active = String.valueOf(BleString.shift(data.get(4), data.get(5)));
                        String light = String.valueOf(BleString.shift(data.get(6), data.get(7)));
                        String deep = String.valueOf(BleString.shift(data.get(8), data.get(9)));
                        String start = DateUtils.long2MinuteString(mSleepSumStartTime);
                        String end = DateUtils.long2MinuteString(mSleepSumEndTime);
                        String date = DateUtils.long2DayString(mSleepSumStartTime);
//                        BleLogs.i(TAG, active + ", " + deep + ", " + light + ", " + start + ", " + end + "," + date);
                        long currentLong = new Date().getTime() / 1000; //second
                        long startLong = currentLong - 24 * 60 * 60 * 30 * 3; //three months ago
                        if (mSleepSumStartTime < startLong || mSleepSumStartTime > currentLong) {
                            BleLogs.e(TAG, "Invalid sleep:" + DateUtils.long2SecondString(mSleepSumStartTime)
                                    + ", currentLong:" + DateUtils.long2SecondString(currentLong)
                                    + ", startLong:" + DateUtils.long2SecondString(startLong));
                        } else {
                            DbUtils.replaceSleepSum(context, active, light, deep, start, end, date);
                        }
                    }
                }
                BleLogs.i(TAG, index + ", " + mSleepSumIndex + ", " + number + ", " + mSleepSumSynch);
                // (number == 0 && mSleepSumSynch == 0)   Not synchronized time the first one send cmd
                // (number > 0)  synchronization time the last one send cmd
                if (index < mSleepSumIndex && ((number == 0 && mSleepSumSynch == 0) || (number > 0 && number == 1))) {
                    BleSend.getInstance().synchSleepSum(context, (index + 1));
                }
                //synchronized completed
                if (mBleImp != null) {
                    if ((index == mSleepSumIndex) && (number == 1)) {
                        mBleImp.onSuccess(BleCode.HISTORYSLEEP, "Completed");
                    } else {
                        mBleImp.onSuccess(BleCode.HISTORYSLEEP, "Synchronizing");
                    }
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_VERSION, action)) {
            if (data != null && data.size() > 9) {
                char d4 = BleString.Integer2Ascii(data.get(4));
                char d5 = BleString.Integer2Ascii(data.get(5));
                char d6 = BleString.Integer2Ascii(data.get(6));
                char d7 = BleString.Integer2Ascii(data.get(7));
                char d8 = BleString.Integer2Ascii(data.get(8));
                char d9 = BleString.Integer2Ascii(data.get(9));
                String version = "" + d4 + d5 + d6 + d7 + d8 + d9;
                BleLogs.i(TAG, "Version:" + version);
                if (mBleImp != null) {
                    mBleImp.onSuccess(BleCode.DEVICEVERSION, version);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_BATTERY, action)) {
            if (data != null && data.size() > 3) {
                String battery = "" + data.get(3);
                BleLogs.i(TAG, "Battery:" + battery);
                if (mBleImp != null) {
                    mBleImp.onSuccess(BleCode.DEVICEBATTERY, battery);
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_FLASH_CLEAR, action)) {
            if (data != null && data.size() > 3) {
                int type = data.get(3);
                if (mBleImp != null) {
                    if (type == 1) {
                        mBleImp.onSuccess(BleCode.CLEARSTEPFLASH, "Completed");
                    } else if (type == 2) {
                        mBleImp.onSuccess(BleCode.CLEARSLEEPFLASH, "Completed");
                    }
                }
            }
        } else if (TextUtils.equals(BleContants.ACTION_BLE_SLEEP_SWITCH, action)) {
            if (data != null && data.size() > 3) {
                String state = "" + data.get(3);
                BleLogs.i(TAG, "SLEEP SWITCH:" + state);
                if (mBleImp != null) {
                    mBleImp.onSuccess(BleCode.SLEEPSWITCH, state);
                }
            }
        }
    }
}
