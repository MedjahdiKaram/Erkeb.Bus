package com.medjahdi.erkebbus.services;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.models.Compost;

public class CompostService {
    private FirebaseServiceContext<Compost> compostServiceContext;

    public CompostService(FirebaseDatabase firebaseDatabase) {
        compostServiceContext = new FirebaseServiceContext<Compost>(firebaseDatabase, "Compost");
    }
    public void InsertInRoot(Compost compost)
    {
        compostServiceContext.InsertInRoot(compost);
    }
    public void append(Compost compost)
    {
        compostServiceContext.append(compost);
    }
    public Compost read()
    {
       return compostServiceContext.read("Compost");
    }

}
