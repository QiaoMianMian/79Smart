package com.smart.library.model;


public class SleepModel {
    private int mSleepActive;   //minute
    private int mSleepDeep;     //minute
    private int mSleepLight;    //minute
    private String mSleepStartTime;  //yy-MM-dd HH:mm
    private String mSleepEndTime;    //yy-MM-dd HH:mm
    private String mSleepDateTime;  //yyyy-MM-dd

    public int getSleepActive() {
        return mSleepActive;
    }

    public void setSleepActive(int mSleepActive) {
        this.mSleepActive = mSleepActive;
    }

    public int getSleepDeep() {
        return mSleepDeep;
    }

    public void setSleepDeep(int mSleepDeep) {
        this.mSleepDeep = mSleepDeep;
    }

    public int getSleepLight() {
        return mSleepLight;
    }

    public void setSleepLight(int mSleepLight) {
        this.mSleepLight = mSleepLight;
    }

    public String getSleepStartTime() {
        return mSleepStartTime;
    }

    public void setSleepStartTime(String mSleepStartTime) {
        this.mSleepStartTime = mSleepStartTime;
    }

    public String getSleepEndTime() {
        return mSleepEndTime;
    }

    public void setSleepEndTime(String mSleepEndTime) {
        this.mSleepEndTime = mSleepEndTime;
    }

    public String getSleepDateTime() {
        return mSleepDateTime;
    }

    public void setSleepDateTime(String mSleepDateTime) {
        this.mSleepDateTime = mSleepDateTime;
    }

    @Override
    public String toString() {
        return "SleepModel{" +
                "mSleepActive=" + mSleepActive +
                ", mSleepDeep=" + mSleepDeep +
                ", mSleepLight=" + mSleepLight +
                ", mSleepStartTime='" + mSleepStartTime + '\'' +
                ", mSleepEndTime='" + mSleepEndTime + '\'' +
                ", mSleepDateTime='" + mSleepDateTime + '\'' +
                '}';
    }
}
