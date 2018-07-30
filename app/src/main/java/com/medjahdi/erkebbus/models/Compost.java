package com.medjahdi.erkebbus.models;

import com.google.firebase.database.IgnoreExtraProperties;
import com.medjahdi.erkebbus.helpers.Common;

import java.io.Serializable;

@IgnoreExtraProperties

public class Compost  implements Serializable {
    public void setRecord(String record) {
        this.record = record;
    }

    private  String busId, lineNumber,cardId,record;
    private  double amount;
    private String hashKey;



    public Compost() {
    }

    public Compost(String busId, String lineNumber, String cardId, double amount) {
        this.busId = busId;
        this.cardId = cardId;
        this.amount = amount;
        this.lineNumber=lineNumber;
        record = Common.getCurrentDateTime();
    }

    public String getRecord() {
        return record;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }
}
