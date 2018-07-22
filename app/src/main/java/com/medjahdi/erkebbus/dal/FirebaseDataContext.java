package com.medjahdi.erkebbus.dal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDataContext<T> {
    private FirebaseDatabase fireBaseDb;
    private DatabaseReference dbReference;
    private final String parentIdKey;


    public FirebaseDataContext(FirebaseDatabase fireBaseDb, final String parentIdKey) {
        this.parentIdKey = parentIdKey;
        this.fireBaseDb = fireBaseDb;
        this.dbReference = this.fireBaseDb.getReference(parentIdKey);

    }

    public void setEvenetListener(ValueEventListener valueEventListener)
    {
        this.dbReference.addValueEventListener(valueEventListener);
    }
    public void create(T object) {
        try {
            this.dbReference.push().setValue(object);
        } catch (Exception ex) {

        }

    }

    public void create(String childIdKey, T object) {
        try {

            this.dbReference.child(childIdKey).push().setValue(object);
        } catch (Exception ex) {

        }
    }


    public void update(String childIdKey, String key, Object value) {
        try {

            this.dbReference.child(childIdKey).child(key).setValue(value);
        } catch (Exception ex) {

        }

    }




}
