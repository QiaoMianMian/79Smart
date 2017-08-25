package com.smart.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

import com.smart.library.blue.BleLogs;
import com.smart.library.model.SleepModel;
import com.smart.library.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbUtils {

    private static String TAG = DbUtils.class.getSimpleName();

    //****************************************** STEP*****************************************

    /**
     * steps written to the TB_STEP table
     *
     * @param context
     * @param step
     * @param time
     */
    public static void replaceStep(Context context, String step, String time, long duration) {
        if (context == null || TextUtils.isEmpty(step) || TextUtils.isEmpty(time)) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("step", step);
        cv.put("time", time);
        cv.put("duration", duration);
        try {
            DbHelper.getInstance(context).replace(DbHelper.TB_STEP, cv, "");
        } catch (DbException e) {
            BleLogs.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the number of steps for a day
     * [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]  <==>  [0:00-23:00]
     *
     * @param context
     * @param day     yyyy-MM-dd
     */
    public static String getOneDaySteps(Context context, String day) {
        String steps = "";
        for (int i = 0; i < 24; i++) {
            String j = "" + i;
            if (i < 10) {
                j = "0" + i;
            }
            String step = "" + getPeriodSteps(context, day + " " + (j + ":00"), day + " " + (j + ":59"));
            if (TextUtils.isEmpty(step)) {
                step = "" + 0;
            }
            if (!TextUtils.isEmpty(steps)) {
                steps += ",";
            }
            steps += step;
        }
        return steps;
    }

    /**
     * Gets the number of durations for a day
     * [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]  <==>  [0:00-23:00]
     *
     * @param context
     * @param day     yyyy-MM-dd
     * @return second
     */
    public static String getOneDayDurations(Context context, String day) {
        String durations = "";
        for (int i = 0; i < 24; i++) {
            String j = "" + i;
            if (i < 10) {
                j = "0" + i;
            }
            String duration = "" + getPeriodDurations(context, day + " " + (j + ":00"), day + " " + (j + ":59"));
            if (TextUtils.isEmpty(duration)) {
                duration = "" + 0;
            }
            if (!TextUtils.isEmpty(durations)) {
                durations += ",";
            }
            durations += duration;
        }
        return durations;
    }

    /**
     * Gets the total number of steps in a database
     *
     * @param context context
     * @param day     yyyy-MM-dd
     * @return total number of steps
     */
    public static int getOneDayCountSteps(Context context, String day) {
        return getPeriodSteps(context, day + " 00:00", day + " 23:59");
    }

    /**
     * Gets the number of steps for a day
     * [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]  <==>  [0:00-23:00]
     *
     * @param context
     * @param date
     */
    public static String getOneDaySteps(Context context, Date date) {
        String day = DateUtils.date2DayString(date);
        return getOneDaySteps(context, day);
    }

    /**
     * @param context   context
     * @param startTime
     * @param endTime
     * @return period step data
     */
    private static int getPeriodSteps(Context context, String startTime, String endTime) {
        int steps = 0;
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + DbHelper.TB_STEP + "  where time >= ? and time <= ?",
                new String[]{startTime, endTime});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    int step = cursor.getInt(cursor.getColumnIndex("step"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    BleLogs.i(TAG, time + ", " + step);
                    steps += step;
                }
            }
        } catch (Exception e) {
            BleLogs.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return steps;
    }

    /**
     * @param context   context
     * @param startTime
     * @param endTime
     * @return period duration data(second)
     */
    private static int getPeriodDurations(Context context, String startTime, String endTime) {
        int durations = 0;
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + DbHelper.TB_STEP + "  where time >= ? and time <= ?",
                new String[]{startTime, endTime});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                    String time = cursor.getString(cursor.getColumnIndex("time"));
                    BleLogs.i(TAG, time + ", " + duration);
                    durations += duration;
                }
            }
        } catch (Exception e) {
            BleLogs.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return durations;
    }

    /**
     * Delete the data between the start time and the end time
     * delete from tv_db  where time > '2016-08-03 00:00' and time < '2016-08-03 24:00'
     *
     * @param context
     * @param starttime
     * @param endtime
     */
    public static void delPeriodSteps(Context context, String starttime, String endtime) {
        try {
            DbHelper.getInstance(context).delete(DbHelper.TB_STEP, "time > ? and time < ?", new String[]{starttime, endtime});
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear the TB_STEP table
     *
     * @param context
     */
    public static void delAllSteps(Context context) {
        DbHelper.getInstance(context).execSql("delete from " + DbHelper.TB_STEP);
    }

    /**
     * Delete the number of steps for a day
     *
     * @param context
     * @param day
     */
    public static void delDaySteps(Context context, String day) {
        try {
            String starttime = day + " 00:00";
            String endtime = day + " 23:59";
            DbHelper.getInstance(context).delete(DbHelper.TB_STEP, "time > ? and time < ?", new String[]{starttime, endtime});
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the date from the tb_step table
     *
     * @param context
     * @return List<yyyy-MM-dd>
     */
    public static List<String> getStepPeriodDate(Context context) {
        List<String> periods = new ArrayList<>();
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + DbHelper.TB_STEP + " order by time desc", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String max = cursor.getString(cursor.getColumnIndex("time"));
                cursor.moveToLast();
                String min = cursor.getString(cursor.getColumnIndex("time"));
                BleLogs.i(TAG, "min:" + min + ",max:" + max);
                periods = DateUtils.getDatePeriods(min, max);
            }
        } catch (Exception e) {
            BleLogs.e(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return periods;
    }

    //****************************************** SLEEP*****************************************


    /**
     * sleep written to the TB_SLEEP table
     *
     * @param context
     * @param active
     * @param deep
     * @param light
     * @param start
     * @param end
     * @param date
     */
    public static void replaceSleepSum(Context context, String active, String light, String deep, String start, String end, String date) {
        if (context == null || TextUtils.isEmpty(active) || TextUtils.isEmpty(deep) || TextUtils.isEmpty(light)
                || TextUtils.isEmpty(start) || TextUtils.isEmpty(end) || TextUtils.isEmpty(date)) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put("active", active);
        cv.put("light", light);
        cv.put("deep", deep);
        cv.put("start", start);
        cv.put("end", end);
        cv.put("date", date);
        try {
            DbHelper.getInstance(context).replace(DbHelper.TB_SLEEP, cv, "");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the date from the tb_sleep table
     *
     * @param context
     * @return List<yyyy-MM-dd>
     */
    public static List<String> getSleepPeriodDate(Context context) {
        List<String> periods = new ArrayList<>();
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + DbHelper.TB_SLEEP + " order by date desc", null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String max = cursor.getString(cursor.getColumnIndex("date"));
                cursor.moveToLast();
                String min = cursor.getString(cursor.getColumnIndex("date"));
                BleLogs.i(TAG, "min:" + min + ",max:" + max);
                periods = DateUtils.getDatePeriods(min, max);
            }
        } catch (Exception e) {
            BleLogs.e(e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return periods;
    }

    /**
     * Gets the number of sleep for a day
     *
     * @param context
     * @param date    yyyy-MM-dd
     */
    public static List<SleepModel> getSleepModel(Context context, String date) {
        List<SleepModel> models = new ArrayList<>();
        Cursor cursor = DbHelper.getInstance(context).rawQuery("select * from " + DbHelper.TB_SLEEP + "  where date == ? ", new String[]{date});
        try {
            if (cursor != null && cursor.getCount() > 0) {
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
//                    cursor.moveToFirst();
                    SleepModel model = new SleepModel();
                    model.setSleepActive(cursor.getInt(cursor.getColumnIndex("active")));
                    model.setSleepDeep(cursor.getInt(cursor.getColumnIndex("deep")));
                    model.setSleepLight(cursor.getInt(cursor.getColumnIndex("light")));
                    model.setSleepStartTime(cursor.getString(cursor.getColumnIndex("start")));
                    model.setSleepEndTime(cursor.getString(cursor.getColumnIndex("end")));
                    models.add(model);
                }
            }
        } catch (Exception e) {
            BleLogs.e(TAG, e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return models;
    }

    /**
     * Clear the TB_SLEEP table
     *
     * @param context
     */
    public static void delAllSleep(Context context) {
        DbHelper.getInstance(context).execSql("delete from " + DbHelper.TB_SLEEP);
    }
}
