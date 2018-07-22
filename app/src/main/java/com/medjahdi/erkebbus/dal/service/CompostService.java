package com.medjahdi.erkebbus.dal.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medjahdi.erkebbus.R;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public class CompostService  extends ServiceBase<Compost>  {

    public CompostService( FirebaseDatabase firebaseDatabase, Context context) {
        super(firebaseDatabase,"Compost",context,context.getString(R.string.sql_query_compost_table_creation));
        SQL_TABLE_AND_FIREBASE_COLLECTION_NAME="Compost";
        ValueEventListener eventlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allChildren = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Compost obj = snapshot.getValue(Compost.class);
                    String hashKey = snapshot.getKey();
                    obj.setHashKey(hashKey);
                    allChildren.add(obj);
                    System.out.println(obj); // to be removed on prod
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        };
        firebaseDataContext.setEvenetListener(eventlistener);
    }


    @Override
    public List<Compost> read() {
        return allChildren;
    }
    @Override
    public List<Compost> db_read(String card_id) {


        List<Compost> recordsList = new ArrayList<Compost>();

        String sql = "SELECT * FROM "+SQL_TABLE_AND_FIREBASE_COLLECTION_NAME+" ORDER BY cardId DESC";
        if (card_id!=null && card_id!="")
            sql = "SELECT * FROM "+SQL_TABLE_AND_FIREBASE_COLLECTION_NAME+" WHERE cardId='"+card_id+"' ORDER BY cardId DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                String cardId  = cursor.getString(cursor.getColumnIndex("cardId"));
                String busId   = cursor.getString(cursor.getColumnIndex("busId"));
                String record  = cursor.getString(cursor.getColumnIndex("record"));
                double amount  =Double.valueOf(cursor.getString(cursor.getColumnIndex("amount")));
                String hashKey = cursor.getString(cursor.getColumnIndex("hashKey"));

                Compost compost = new Compost();
                compost.setBusId(busId);
                compost.setRecord(record);
                compost.setAmount(amount);
                compost.setHashKey(hashKey);
                compost.setCardId(cardId);
                recordsList.add(compost);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    @Override
    public void create(Compost compost) {
        firebaseDataContext.create(compost);
    }
    @Override
    public void create(String childIdKey, Compost compost) {
        firebaseDataContext.create(childIdKey, compost);
    }
    public boolean db_create(Compost compost)
    {
        ContentValues values = new ContentValues();
        values.put("busId",  compost.getBusId());
        values.put("cardId", compost.getCardId());
        values.put("record", compost.getRecord());
        values.put("amount", compost.getAmount());
        values.put("hashKey",compost.getHashKey());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(SQL_TABLE_AND_FIREBASE_COLLECTION_NAME, null, values) > 0;
        db.close();

        return createSuccessful;
    }
    @Override
    public void update(String childIdKey, String key, Object value) {
        firebaseDataContext.update(childIdKey, key, value);
    }

    public void updateOffline(Compost compost) {
        // something
    }
}
