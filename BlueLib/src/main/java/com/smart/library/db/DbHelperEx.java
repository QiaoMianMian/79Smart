package com.smart.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public abstract class DbHelperEx extends SQLiteOpenHelper {
    private static final String TAG = DbHelperEx.class.getSimpleName();

    private boolean mIsNewDB = false;
    SQLiteQueryBuilder msqlQB = null;
    private final Context mContext;
    private boolean mUpdateResult = true;

    public DbHelperEx(Context context, String dataBaseName, int dataBaseVersion) {
        super(context, dataBaseName, null, dataBaseVersion);
        this.mContext = context;
        this.msqlQB = new SQLiteQueryBuilder();
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            if (!this.mUpdateResult) {
                if (db != null) {
                    db.close();
                }
                this.mContext.deleteDatabase(getDbName());
                getWritableDatabase();
            }
        } catch (SQLiteException ex) {
            this.mContext.deleteDatabase(getDbName());
        } catch (IllegalStateException localIllegalStateException) {
        }
    }

    public void onCreate(SQLiteDatabase db) {
        this.mUpdateResult = true;
        this.mIsNewDB = true;
        db.beginTransaction();
        try {
            onCreateTables(db);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public boolean isExistTable(SQLiteDatabase db, String tableName) {
        boolean result = false;
        Cursor cursor = null;
        String where = "type='table' and name='" + tableName + "'";
        try {
            cursor = db.query("sqlite_master", null, where, null, null, null, null);
            if ((cursor != null) && (cursor.moveToFirst())) {
                result = true;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    private boolean isExistColumnInTable(SQLiteDatabase db, String tableName, String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            String[] columns = {columnName};
            cursor = db.query(tableName, columns, null, null, null, null, null);
            if ((cursor != null) && (cursor.getColumnIndex(columnName) >= 0)) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    public void addColumnToTable(SQLiteDatabase db, String tableName, String columnName, String columnType, String defaultValue) {
        if (!isExistColumnInTable(db, tableName, columnName)) {
            db.beginTransaction();
            try {
                String updateSql = "ALTER TABLE " + tableName + " ADD " + columnName + " " + columnType;
                db.execSQL(updateSql);
                if (defaultValue != null) {
                    if (columnType.equals("text")) {
                        defaultValue = "'" + defaultValue + "'";
                    }
                    updateSql = "update " + tableName + " set " + columnName + " = " + defaultValue;
                    db.execSQL(updateSql);
                }
                db.setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
            }
        }
    }

    public void execSql(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            return db.rawQuery(sql, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String sortOrder) {
        Cursor result = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            result = db.query(tableName, projection, selection, selectionArgs, groupBy, having, sortOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Cursor queryCrossTables(String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result = null;
        synchronized (this.msqlQB) {
            this.msqlQB.setTables(tableName);
            try {
                SQLiteDatabase db = getReadableDatabase();
                result = this.msqlQB.query(db, projection, selection, selectionArgs, null, null, sortOrder);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public long insert(String tableName, ContentValues initialValues) throws DbException {
        SQLiteDatabase db = getWritableDatabase();
        long rowId = 0L;
        try {
            rowId = db.insert(tableName, null, initialValues);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return rowId;
    }

    public int delete(String tableName, String selection, String[] selectionArgs) throws DbException {
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;
        try {
            count = db.delete(tableName, selection, selectionArgs);
        } catch (Exception e) {
            throw new DbException(e);
        }

        return count;
    }

    public long replace(String tableName, ContentValues values, String selection) throws DbException {
        SQLiteDatabase db = getWritableDatabase();
        long count = 0;
        try {
            count = db.replace(tableName, selection, values);
        } catch (Exception e) {
            throw new DbException(e);
        }
        return count;
    }

    public int update(String tableName, ContentValues values, String selection, String[] selectionArgs) throws DbException {
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;
        try {
            count = db.update(tableName, values, selection, selectionArgs);
        } catch (Exception e) {
            throw new DbException(e);
        }

        return count;
    }

    public void exec(String sql) throws DbException {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            throw new DbException(e);
        }
    }

    public boolean isNewDB() {
        return this.mIsNewDB;
    }

    public boolean openDBWithWorldReadable() {
        try {
            close();
            if (this.mContext.openOrCreateDatabase(getDbName(), 1, null) == null) {
                return false;
            }
        } catch (Exception e) {
            close();
            return false;
        }
        return true;
    }

    public void beginTransaction() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                db.beginTransaction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTransactionSuccessful() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (db != null) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endTransaction() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.endTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract int getDbCurrentVersion();

    public abstract String getDbName();

    public abstract void onCreateTables(SQLiteDatabase paramSQLiteDatabase);

    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    // public abstract void onAddUpgrades(ArrayList<UpgradeDB> paramArrayList);

    public abstract class UpgradeDB {
        public UpgradeDB() {
        }
        public abstract boolean onUpgradeDB(SQLiteDatabase paramSQLiteDatabase);
    }

}
