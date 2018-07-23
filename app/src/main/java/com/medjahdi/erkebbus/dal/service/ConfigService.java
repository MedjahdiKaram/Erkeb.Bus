package com.medjahdi.erkebbus.dal.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.medjahdi.erkebbus.R;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Config;

import java.util.ArrayList;

public class ConfigService extends DatabaseHandler {
    public ConfigService(Context context, String dbName) {
        super(context, dbName, context.getString(R.string.sql_query_config_table_creation));

    }

    public boolean db_create(Config obj) {
        ContentValues values = new ContentValues();
        values.put("busId", obj.getBusId());
        values.put("minimumAmountToCompost", obj.getMinimumAmountToCompost());
        values.put("compostAmount", obj.getCompostAmount());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("Config", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public ArrayList<Config> db_read() {
        try {
            ArrayList<Config> configList = null;
            String sql = "SELECT * FROM Config ORDER BY id DESC";

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                do {
                    if (configList==null)
                        configList = new ArrayList<Config>();
                    String busId = cursor.getString(cursor.getColumnIndex("busId"));
                    Float compostAmount = Float.valueOf(cursor.getString(cursor.getColumnIndex("compostAmount")));
                    Float minimumAmountToCompost = Float.valueOf(cursor.getString(cursor.getColumnIndex("minimumAmountToCompost")));
                    Config config = new Config(busId, minimumAmountToCompost, compostAmount);

                    configList.add(config);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();
            return configList;
        } catch (Exception ex) {
            return null;
        }
    }


    public int db_count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM Config";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
}
