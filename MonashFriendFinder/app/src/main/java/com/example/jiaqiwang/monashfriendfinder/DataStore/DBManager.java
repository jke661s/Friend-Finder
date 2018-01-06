package com.example.jiaqiwang.monashfriendfinder.DataStore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jiaqi Wang on 5/2/2017.
 */

public class DBManager {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    private final Context context;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBStructure.tableEntry.TABLE_NAME + " (" + DBStructure.tableEntry._ID + " INTEGER PRIMARY KEY," + DBStructure.tableEntry.COLUMN_ID + TEXT_TYPE + COMMA_SEP +
            DBStructure.tableEntry.COLUMN_NAME + TEXT_TYPE + " );";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBStructure.tableEntry.TABLE_NAME;
    private String[] projection = { DBStructure.tableEntry.COLUMN_ID,
            DBStructure.tableEntry.COLUMN_NAME };

    private MySQLiteOpenHelper myDBHelper;
    private SQLiteDatabase db;

    public DBManager(Context ctx) {
        this.context = ctx;
        myDBHelper = new MySQLiteOpenHelper(context);
    }

    public DBManager open() throws SQLException {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    public long insertSport(String id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBStructure.tableEntry.COLUMN_ID, id);
        values.put(DBStructure.tableEntry.COLUMN_NAME, name);
        return db.insert(DBStructure.tableEntry.TABLE_NAME, null, values);
    }

    public Cursor getAllSports() {
        return db.query(DBStructure.tableEntry.TABLE_NAME, projection, null, null, null, null,null);
    }

    public void close() {
        myDBHelper.close();
    }


    private static class MySQLiteOpenHelper extends SQLiteOpenHelper {
        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }


}
