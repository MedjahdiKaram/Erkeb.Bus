package com.medjahdi.erkebbus.dal.service;


import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.models.Card;

import java.util.List;

public abstract class ServiceBase<T> extends DatabaseHandler {
    List<T> allChildren ;
    FirebaseDataContext<T> firebaseDataContext;
    abstract List<T> read();
    abstract void create(T input);
    abstract void create(String childIdKey, T input);
    abstract void update(String childIdKey, String key, Object value);
    public ServiceBase(FirebaseDatabase firebaseDatabase, String parendIdKey, Context context, String creationQuery) {
        super(context,"Erkab.bus",creationQuery);
        firebaseDataContext = new FirebaseDataContext<T>(firebaseDatabase, parendIdKey);
    }
}
