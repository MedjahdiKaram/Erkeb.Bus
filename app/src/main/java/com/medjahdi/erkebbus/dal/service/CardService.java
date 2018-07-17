package com.medjahdi.erkebbus.dal.service;

import com.google.firebase.database.FirebaseDatabase;
import com.medjahdi.erkebbus.models.Card;

import java.util.ArrayList;

public class CardService extends ServiceBase<Card> {

    public CardService(FirebaseDatabase firebaseDatabase) {
        super(firebaseDatabase, "Card");
    }

    @Override
    public void append(Card card) {
        firebaseDataContext.append(card);
    }


    @Override
    public ArrayList<Card> read() {
        allChildren = firebaseDataContext.getAllChildren();
        return allChildren;
    }

}
