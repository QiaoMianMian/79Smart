package com.smart.library.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper extends DbHelperEx {
    public static final String DB_NAME = "blue_data.db";
    public static int DB_VERSION = 2;
    public static final String TB_STEP = "tb_step";
    public static final String TB_SLEEP = "tb_sleep";
    public static DbHelper instance = null;

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context);
        }
        return instance;
    }

    public DbHelper(Context context) {
        super(context, DB_NAME, DB_VERSION);
    }

    @Override
    public int getDbCurrentVersion() {
        return DB_VERSION;
    }

    @Override
    public String getDbName() {
        return DB_NAME;
    }

    @Override
    public void onCreateTables(SQLiteDatabase db) {
        db.execSQL("CREATE table IF NOT EXISTS "
                + TB_STEP
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,step INTEGER,time TEXT UNIQUE,duration INTEGER);");

        db.execSQL("CREATE table IF NOT EXISTS "
                + TB_SLEEP
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT,active TEXT,light TEXT, deep TEXT,start TEXT,end TEXT,date TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != DB_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + TB_STEP);
            db.execSQL("DROP TABLE IF EXISTS " + TB_SLEEP);
            onCreate(db);
        }
    }
}
