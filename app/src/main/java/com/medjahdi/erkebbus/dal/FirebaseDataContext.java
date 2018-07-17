package com.medjahdi.erkebbus.dal;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDataContext<T> implements ValueEventListener {
    private FirebaseDatabase fireBaseDb;
    private DatabaseReference dbReference;
    private final String parentIdKey;
    private ArrayList<T> allChildren = null;


    public FirebaseDataContext(FirebaseDatabase fireBaseDb, final String parentIdKey) {
        this.parentIdKey = parentIdKey;
        this.fireBaseDb = fireBaseDb;
        this.dbReference = this.fireBaseDb.getReference(parentIdKey);
    }


    public void append(T object) {
        try {
            this.dbReference.push().setValue(object);
        } catch (Exception ex) {

        }

    }

    public void append(String childIdKey, T object) {
        try {
            this.dbReference.child(childIdKey).push().setValue(object);
        } catch (Exception ex) {

        }
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        allChildren=new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren())
        {
            Object obj = snapshot.getValue();
            T typedObj = (T) obj;
            allChildren.add(typedObj);
            System.out.println(typedObj); // to be removed on prod
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        System.out.println("The read failed: " + databaseError.getCode());
    }


    public ArrayList<T> getAllChildren() {
        return allChildren;
    }
}
