package com.medjahdi.erkebbus.dal.service;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;

import java.util.ArrayList;
import java.util.List;

public abstract class ServiceBase<T> {
    protected List<T> allChildren = new ArrayList<T>();
    public FirebaseDataContext<T> firebaseDataContext;
    public abstract List<T> read();
    public abstract void create(T input);
    public abstract void create(String childIdKey, T input);
    protected abstract void update(String childIdKey, String key, Object value);
    public ServiceBase(FirebaseDatabase firebaseDatabase, String parendIdKey) {
        firebaseDataContext = new FirebaseDataContext<T>(firebaseDatabase, parendIdKey);
    }
}
