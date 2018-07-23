package com.medjahdi.erkebbus.dal.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medjahdi.erkebbus.R;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.helpers.Common;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public class CardService extends ServiceBase<Card> {
    public CardService(FirebaseDatabase firebaseDatabase, Context context) {
        super(firebaseDatabase, "Card", context, context.getString(R.string.sql_query_card_table_creation));
        SQL_TABLE_AND_FIREBASE_COLLECTION_NAME = "Card";
        ValueEventListener eventlistener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                allChildren = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Card obj = snapshot.getValue(Card.class);
                    String hashKey = snapshot.getKey();
                    obj.setHashKey(hashKey);
                    allChildren.add(obj);
                    System.out.println(obj); // to be removed on prod
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                checkFirebaseConnectivityStatus(databaseError);
                System.out.println("card reader failed " + databaseError.getCode());
            }
        };
        firebaseDataContext.setEvenetListener(eventlistener);
    }

    @Override
    public void create(Card card) {
        firebaseDataContext.create(card);
    }

    @Override
    public void create(String childIdKey, Card card) {
        firebaseDataContext.create(childIdKey, card);
    }

    @Override
    public void update(String childIdKey, String key, Object value) {
        firebaseDataContext.update(childIdKey, key, value);
    }

    @Override
    public void delete(Card object) {
        firebaseDataContext.delete(object.getHashKey());
    }

    @Override
    public ArrayList<Card> read() {
        return allChildren;
    }

    @Override
    public Card read(String cardId) {
        for (Card card:allChildren)
        {
            String fbCardId=card.getCardId();
            if (card!=null && card.getCardId() == fbCardId)
                return card;
        }
        return null;
    }


    public void updateBalance(String childIdKey, double value) {
        this.update(childIdKey, "currentBalance", value);
        this.update(childIdKey, "updateRecord", Common.getCurrentDateTime());
    }

    public void updateOfflineBalance(String childIdKey, double value) {
        //something to update teh sqlLite
    }

    @Override
    public boolean db_create(Card obj) {
        ContentValues values = new ContentValues();
        values.put("cardId", obj.getCardId());
        values.put("creationRecord", obj.getCreationRecord());
        values.put("updateRecord", obj.getUpdateRecord());
        values.put("currentBalance", obj.getCurrentBalance());
        values.put("hashKey", obj.getHashKey());
        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert("card", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    @Override
    public ArrayList<Card> db_read(String card_id) {
        ArrayList<Card> recordsList = new ArrayList<Card>();
        String sql = "SELECT * FROM " + SQL_TABLE_AND_FIREBASE_COLLECTION_NAME + " ORDER BY cardId DESC";
        if (card_id != null && card_id != "")
            sql = "SELECT * FROM " + SQL_TABLE_AND_FIREBASE_COLLECTION_NAME + " WHERE cardId='" + card_id + "' ORDER BY cardId DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                String cardId = cursor.getString(cursor.getColumnIndex("cardId"));
                String creationRecord = cursor.getString(cursor.getColumnIndex("creationRecord"));
                String updateRecord = cursor.getString(cursor.getColumnIndex("updateRecord"));
                double currentBalance = Double.valueOf(cursor.getString(cursor.getColumnIndex("currentBalance")));
                String hashKey = cursor.getString(cursor.getColumnIndex("hashKey"));

                Card card = new Card();
                card.setCardId(cardId);
                card.setCreationRecord(creationRecord);
                card.setUpdateRecord(updateRecord);
                card.setCurrentBalance(currentBalance);
                card.setHashKey(hashKey);
                recordsList.add(card);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public boolean db_update(Card obj) {
        try {
            ContentValues values = new ContentValues();
            values.put("cardId", obj.getCardId());
            values.put("creationRecord", obj.getCreationRecord());
            values.put("updateRecord", obj.getUpdateRecord());
            values.put("currentBalance", obj.getCurrentBalance());
            values.put("hashKey", obj.getHashKey());
            String where = "cardId = ?";

            String[] whereArgs = {obj.getCardId()};

            SQLiteDatabase db = this.getWritableDatabase();

            boolean updateSuccessful = db.update(SQL_TABLE_AND_FIREBASE_COLLECTION_NAME, values, where, whereArgs) > 0;
            db.close();

            return updateSuccessful;
        } catch (Exception ex) {

            return false;
        }
    }
}



