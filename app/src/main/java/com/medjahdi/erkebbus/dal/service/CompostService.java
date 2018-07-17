package com.medjahdi.erkebbus.dal.service;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;

public class CompostService extends ServiceBase<Compost> {

    public CompostService(FirebaseDatabase firebaseDatabase) {
      super(firebaseDatabase,"Compost");
    }


    @Override
    public ArrayList<Compost> read() {
        allChildren = firebaseDataContext.getAllChildren();
        return allChildren;
    }
    @Override
    public void append(Compost compost) {
        firebaseDataContext.append(compost);
    }

}
