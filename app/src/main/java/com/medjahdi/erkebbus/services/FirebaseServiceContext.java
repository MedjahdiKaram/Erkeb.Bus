package com.medjahdi.erkebbus.services;

import android.os.Debug;
import android.os.Message;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseServiceContext<T> {
    private FirebaseDatabase fireBaseDb;
    private DatabaseReference dbReference;
    private ValueEventListener messageListener;
    private final String parentIdKey;
    private T result;

    public FirebaseServiceContext(FirebaseDatabase fireBaseDb, final String parentIdKey) {
        this.parentIdKey=parentIdKey;
        this.fireBaseDb = fireBaseDb;
        dbReference = this.fireBaseDb.getReference();
        messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {

                     result =(T)  messageSnapshot.child(parentIdKey).getValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
            }

        };
    }

    public void InsertInRoot(T object) {
        try {
            this.dbReference.setValue(object);
        } catch (Exception ex) {

        }

    }

    public void append(T object) {
        try {
            this.dbReference.child(parentIdKey).push().setValue(object);
        } catch (Exception ex) {

        }

    }

    public void append(String childIdKey, T object) {
        try {
            this.dbReference.child(parentIdKey).child(childIdKey).push().setValue(object);
        } catch (Exception ex) {
        }

    }

    public T read(String specificParentIdKey)
    {
        DatabaseReference  dbSpeceficReference= fireBaseDb.getInstance().getReference(specificParentIdKey);
        dbSpeceficReference.addValueEventListener(messageListener);
        return result;
    }







}
