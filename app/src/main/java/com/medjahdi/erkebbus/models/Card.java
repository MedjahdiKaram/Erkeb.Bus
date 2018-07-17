package com.medjahdi.erkebbus.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@IgnoreExtraProperties

public class Card implements Serializable {


    private  String cardId,creationRecord, updateRecord;
    private  double currentBalance;



    public Card() {
    }

    public Card(String cardId, String creationRecord, String updateRecord, double currentBalance) {
        this.cardId = cardId;
        this.creationRecord = creationRecord;
        this.updateRecord = updateRecord;
        this.currentBalance = currentBalance;
    }

    public Card(String cardId, double currentBalance) {
        this.cardId = cardId;
        this.currentBalance = currentBalance;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat moment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.creationRecord = this.updateRecord = moment.format(cal.getTime());
    }

    public Card(String cardId, String creationRecord, double currentBalance) {
        this.cardId = cardId;
        this.creationRecord = creationRecord;
        this.updateRecord = creationRecord;
        this.currentBalance = currentBalance;

    }

    public Card(String cardId) {

        this.cardId = cardId;

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat moment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        creationRecord = updateRecord = moment.format(cal.getTime());
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCreationRecord() {
        return creationRecord;
    }

    public void setCreationRecord(String creationRecord) {
        this.creationRecord = creationRecord;
    }

    public String getUpdateRecord() {
        return updateRecord;
    }

    public void setUpdateRecord(String updateRecord) {
        this.updateRecord = updateRecord;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }
}
