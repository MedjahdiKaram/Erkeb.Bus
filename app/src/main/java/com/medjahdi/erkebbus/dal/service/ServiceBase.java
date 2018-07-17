package com.medjahdi.erkebbus.dal.service;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;

import java.util.ArrayList;

public abstract class ServiceBase<T> {
    protected ArrayList<T> allChildren = new ArrayList<T>();
    public FirebaseDataContext<T> firebaseDataContext;
    public abstract ArrayList<T> read();
    public abstract void append(T input);
    public ServiceBase(FirebaseDatabase firebaseDatabase, String parendIdKey) {
        firebaseDataContext = new FirebaseDataContext<T>(firebaseDatabase, parendIdKey);
    }
}
