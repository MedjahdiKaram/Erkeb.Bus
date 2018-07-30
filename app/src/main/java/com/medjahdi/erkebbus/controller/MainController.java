package com.medjahdi.erkebbus.controller;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medjahdi.erkebbus.dal.service.CardService;
import com.medjahdi.erkebbus.dal.service.CompostService;
import com.medjahdi.erkebbus.models.Card;
import com.medjahdi.erkebbus.models.Compost;
import com.medjahdi.erkebbus.models.Config;

import java.util.ArrayList;

public class MainController {
    private Config configuration;
    private CompostService compostService;
    private CardService cardService;
    private FirebaseDatabase fireBaseDatabase;
    private Card card;



    public MainController() {
    }

    public MainController(Config configuration, FirebaseDatabase fireBaseDatabase, Context context) {
        this.configuration = configuration;
         this.fireBaseDatabase=fireBaseDatabase;
        this.compostService = new CompostService(fireBaseDatabase,context);
        this.cardService =  new CardService(fireBaseDatabase,context);;
    }

    public double[] runCompostTransaction(String cardId) {
        this.card = cardService.read(cardId);
        float compostAmount = configuration.getCompostAmount();
        Compost compost = new Compost(configuration.getBusId(),configuration.getLineNumber(), card.getCardId(), compostAmount);
        double result[]={0,0};
        double oldBalance=card.getCurrentBalance();
        result[0]=oldBalance;
        double newBalance = oldBalance- compostAmount;
        if (newBalance >= configuration.getMinimumAmountToCompost()) {
            cardService.updateBalance(card.getHashKey(), newBalance);
            cardService.updateOfflineBalance(card.getHashKey(), newBalance);
            result[1]=newBalance;
            compostService.create(compost);
        }
        return result;
    }




}
