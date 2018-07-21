package com.medjahdi.erkebbus.dal.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.dal.DatabaseHandler;
import com.medjahdi.erkebbus.dal.FirebaseDataContext;
import com.medjahdi.erkebbus.helpers.Common;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public class CardService extends ServiceBase<Card>  {

    public CardService(FirebaseDatabase firebaseDatabase, Context context, String creationQuery) {
        super(firebaseDatabase,"Card",context,creationQuery);
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
    public List<Card> read() {
        allChildren = firebaseDataContext.getAllChildren();
        return allChildren;
    }


    public void updateBalance(String childIdKey, double value) {
        this.update(childIdKey, "currentBalance", value);
        this.update(childIdKey, "updateRecord", Common.getCurrentDateTime());
    }

    public void updateOfflineBalance(String childIdKey, double value) {
       //something to update teh sqlLite
    }
    public boolean db_create(Card card) {

        ContentValues values = new ContentValues();

        values.put("cardId", card.getCardId());
        values.put("creationRecord", card.getCreationRecord());
        values.put("updateRecord", card.getUpdateRecord());
        values.put("currentBalance", card.getCurrentBalance());
        values.put("hashKey", card.getHashKey());


        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("cards", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int db_count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM cards";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }
}
