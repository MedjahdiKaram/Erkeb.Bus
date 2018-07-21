package com.medjahdi.erkebbus.dal.service;

import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.R;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public class CompostService  extends ServiceBase<Compost>  {

    public CompostService( FirebaseDatabase firebaseDatabase, Context context,String creationQuery) {
        super(firebaseDatabase,"Compost",context,creationQuery);
    }


    @Override
    public List<Compost> read() {
        allChildren = firebaseDataContext.getAllChildren();
        return allChildren;
    }
    @Override
    public void create(Compost compost) {
        firebaseDataContext.create(compost);
    }
    @Override
    public void create(String childIdKey, Compost compost) {
        firebaseDataContext.create(childIdKey, compost);
    }

    @Override
    public void update(String childIdKey, String key, Object value) {
        firebaseDataContext.update(childIdKey, key, value);
    }

    public void updateOffline(Compost compost) {
        // something
    }
}
