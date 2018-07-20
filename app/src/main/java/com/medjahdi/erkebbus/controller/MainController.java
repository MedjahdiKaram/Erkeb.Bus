package com.medjahdi.erkebbus.controller;

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
    private ArrayList<Card> allCards;
    private FirebaseDatabase fireBaseDatabase;
    private Card card;


    public MainController() {
    }

    public MainController(Config configuration, CompostService compostService, CardService cardService,ArrayList<Card> allCards) {
        this.configuration   = configuration;
        this.compostService  = compostService;
        this.cardService     = cardService   ;
        this.allCards        = allCards;
    }

    public boolean runCompostTransaction(Card card, DatabaseError error)
    {
        this.card=card;
        boolean result = false;
        float compostAmount= configuration.getCompostAmount();
        Compost compost = new Compost(configuration.getBusId(),card.getCardId(),compostAmount);
        boolean isFirebaseUp=this.testFirebaseِConnectivity(error);
        if (isFirebaseUp)
        {
            result= onlineCompost(compost, compostAmount);
        }
        else
        {
            result = offlineCompost(compost, compostAmount);
        }
        return result;

    }


    private boolean onlineCompost( Compost compost, float compostAmount)
    {
        compostService.create(compost);
        double newBalance=card.getCurrentBalance()- compostAmount;
        if (newBalance>configuration.getMinimumAmountToCompost())
        {
            cardService.updateBalance(card.getHashKey(),newBalance);
            cardService.updateOfflineBalance(card.getHashKey(),newBalance);
            return true;
        }
        return false;
    }
    private boolean offlineCompost (Compost compost, float compostAmount) {
        double newBalance=card.getCurrentBalance()- compostAmount;
        if (newBalance>configuration.getMinimumAmountToCompost())
        {
            cardService.updateOfflineBalance(card.getHashKey(),newBalance);
            compostService.updateOffline(compost);
            return true;
        }
        return false;
    }

    protected boolean testFirebaseِConnectivity(DatabaseError error) {
        return  error.getCode()==DatabaseError.DISCONNECTED || error.getCode()==DatabaseError.UNAVAILABLE || error.getCode()==DatabaseError.NETWORK_ERROR;
    }
}
