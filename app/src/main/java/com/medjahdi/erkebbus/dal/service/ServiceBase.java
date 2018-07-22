package com.medjahdi.erkebbus.dal.service;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceBase<T> extends DatabaseHandler {
    ArrayList<T> allChildren ;
    public FirebaseDataContext<T> firebaseDataContext;
    static String SQL_TABLE_AND_FIREBASE_COLLECTION_NAME;
    abstract List<T> read();
    abstract void create(T input);
    abstract void create(String childIdKey, T input);
    abstract void update(String childIdKey, String key, Object value);
    abstract boolean db_create(T instance);
    abstract List<T> db_read(String card_id);
    public ServiceBase(FirebaseDatabase firebaseDatabase, String parendIdKey, Context context, String creationQueryFields) {

        super(context,"Erkab.bus",creationQueryFields);
        firebaseDataContext = new FirebaseDataContext<T>(firebaseDatabase, parendIdKey);
    }
    public int db_count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM "+SQL_TABLE_AND_FIREBASE_COLLECTION_NAME;
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
}
