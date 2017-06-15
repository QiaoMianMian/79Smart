package com.smart.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.data;

public class DateUtils {

    public static final String FORMAT_DATE_01 = "yyyy-MM-dd";

    public static final String FORMAT_DATE_02 = "yyyy-MM-dd HH";

    public static final String FORMAT_DATE_03 = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_DATE_04 = "yyyy-MM-dd HH:mm:ss";

    //****************************** string 2 date *******************************

    /**
     * @param time format
     * @return date
     */
    public static Date string2Date(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param time "yyyy-MM-dd"
     * @return date
     */
    public static Date dayString2Date(String time) {
        return string2Date(time, FORMAT_DATE_01);
    }

    /**
     * @param time "yyyy-MM-dd HH"
     * @return date
     */
    public static Date hourString2Date(String time) {
        return string2Date(time, FORMAT_DATE_02);
    }

    /**
     * @param time "yyyy-MM-dd HH:mm"
     * @return date
     */
    public static Date minuteString2Date(String time) {
        return string2Date(time, FORMAT_DATE_03);
    }

    /**
     * @param time "yyyy-MM-dd HH:mm:ss"
     * @return date
     */
    public static Date secondString2Date(String time) {
        return string2Date(time, FORMAT_DATE_04);
    }

    //****************************** string 2 long *******************************

    /**
     * @param time format
     * @return microsecond
     */
    public static long string2Long(String time, String format) {
        long microsecond = 0;
        try {
            microsecond = new SimpleDateFormat(format).parse(time).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return microsecond;
    }

    /**
     * @param "yyyy-MM-dd"
     * @return microsecond
     */
    public static long dayString2Long(String time) {
        return string2Long(time, FORMAT_DATE_01);
    }

    /**
     * @param "yyyy-MM-dd HH"
     * @return microsecond
     */
    public static long hourString2Long(String time) {
        return string2Long(time, FORMAT_DATE_02);
    }

    /**
     * @param "yyyy-MM-dd HH:mm"
     * @return microsecond
     */
    public static long minuteString2Long(String time) {
        return string2Long(time, FORMAT_DATE_03);
    }

    /**
     * @param "yyyy-MM-dd HH:mm:ss"
     * @return microsecond
     */
    public static long secondString2Long(String time) {
        return string2Long(time, FORMAT_DATE_04);
    }

    //****************************** date 2 string *******************************

    /**
     * @param date
     * @return format
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd"
     */
    public static String date2DayString(Date date) {
        return date2String(date, FORMAT_DATE_01);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd HH"
     */
    public static String date2HourString(Date date) {
        return date2String(date, FORMAT_DATE_02);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String date2MinuteString(Date date) {
        return date2String(date, FORMAT_DATE_03);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String date2SecondString(Date date) {
        return date2String(date, FORMAT_DATE_04);
    }

    //****************************** long 2 string *******************************

    /**
     * @param microsecond
     * @return format
     */
    public static String long2String(long microsecond, String format) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(microsecond * 1000);
        return date2String(c.getTime(), format);
    }

    /**
     * @param microsecond
     * @return "yyyy-MM-dd"
     */
    public static String long2DayString(long microsecond) {
        return long2String(microsecond, FORMAT_DATE_01);
    }

    /**
     * @param microsecond
     * @return "yyyy-MM-dd HH"
     */
    public static String long2HourString(long microsecond) {
        return long2String(microsecond, FORMAT_DATE_02);
    }

    /**
     * @param microsecond
     * @return "mm"
     */
    public static String long2Minute(long microsecond) {
        return long2String(microsecond, "mm");
    }

    /**
     * @param microsecond
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String long2MinuteString(long microsecond) {
        return long2String(microsecond, FORMAT_DATE_03);
    }

    /**
     * @param microsecond
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String long2SecondString(long microsecond) {
        return long2String(microsecond, FORMAT_DATE_04);
    }

    //*****************************************************************

    /**
     * @param year
     * @param month
     * @param day
     * @return "yyyy-MM-dd"
     */
    public static String combinDayString(int year, int month, int day) {
        return year + "-" + month + "-" + day;
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @return "yyyy-MM-dd HH"
     */
    public static String combinHourString(int year, int month, int day, int hour) {
        return year + "-" + month + "-" + day + " " + hour;
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @return "yyyy-MM-dd HH:mm"
     */
    public static String combinMinuteString(int year, int month, int day, int hour, int minute) {
        return year + "-" + month + "-" + day + " " + hour + ":" + minute;
    }

    //***************************************************************

    /**
     * Get the whole 10 minutes per hour
     *
     * @param microsecond
     */
    public static String long2TenString(long microsecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(microsecond * 1000);
        String hour = date2HourString(c.getTime());
        String second = Integer.parseInt(long2Minute(microsecond)) / 10 + "0";
        return hour + ":" + second;
    }

    /**
     * According to the start time, end time to get all the two time periods within the date
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public static List<String> getDatePeriods(String starttime, String endtime) {
        List<String> periods = new ArrayList<String>();
        Date start = dayString2Date(starttime);
        Date end = dayString2Date(endtime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date temp = calendar.getTime();
        long endTime = end.getTime();
        while (temp.before(end) || temp.getTime() == endTime) {
            periods.add(date2DayString(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            temp = calendar.getTime();
        }
        return periods;
    }

    /**
     * @param sleepStartTime
     * @param sleepEndTime
     * @return
     */
    public static long getSleepTime(String sleepStartTime, String sleepEndTime) {
        long mSleepSumStartTime = DateUtils.secondString2Long(sleepStartTime);
        long mSleepSumEndTime = DateUtils.secondString2Long(sleepEndTime);
        long sleepTime = (mSleepSumEndTime - mSleepSumStartTime);
        return sleepTime;
    }

}
