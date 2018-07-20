package com.medjahdi.erkebbus.dal.service;


import java.util.List;

interface IServiceBase<T> {

    List<T> read();
    void create(T input);
    void create(String childIdKey, T input);
    void update(String childIdKey, String key, Object value);
    /*public ServiceBase(FirebaseDatabase firebaseDatabase, String parendIdKey) {
        firebaseDataContext = new FirebaseDataContext<T>(firebaseDatabase, parendIdKey);
    }*/
}
