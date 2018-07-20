package com.medjahdi.erkebbus.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    protected static String DATABASE_NAME;
    protected static String SQL_CREATION_QUERY;

    public DatabaseHandler(Context context, String dbName, String creationQuery) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        DATABASE_NAME=dbName;
        SQL_CREATION_QUERY=creationQuery;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATION_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS students";
        db.execSQL(sql);

        onCreate(db);
    }

}