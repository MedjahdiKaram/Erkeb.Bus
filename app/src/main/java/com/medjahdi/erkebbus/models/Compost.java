package com.medjahdi.erkebbus.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.medjahdi.erkebbus.helpers.Common;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties

public class Compost  implements Serializable {
    public void setRecord(String record) {
        this.record = record;
    }

    private  String busId, cardId,record;
    private  double amount, longitude, latitude;



    public Compost() {
    }

    public Compost(String busId, String cardId, double amount) {
        this.busId = busId;
        this.cardId = cardId;
        this.amount = amount;
        record = Common.getCurrentDateTime();
    }

    public String getRecord() {
        return record;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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
}
