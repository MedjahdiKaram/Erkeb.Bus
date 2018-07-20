package com.medjahdi.erkebbus.dal.service;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.helpers.Common;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;

import java.util.ArrayList;
import java.util.List;

public class CardService extends ServiceBase<Card> {

    public CardService(FirebaseDatabase firebaseDatabase) {
        super(firebaseDatabase, "Card");
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
    protected void update(String childIdKey, String key, Object value) {
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

}
