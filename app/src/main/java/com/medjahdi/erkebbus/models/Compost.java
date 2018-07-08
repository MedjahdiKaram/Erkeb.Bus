package com.medjahdi.erkebbus.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Compost {
    private String busId, cardId,record;
    private  double amount, longitude, latitude;



    public Compost(String busId, String cardId, double amount) {
        this.busId = busId;
        this.cardId = cardId;
        this.amount = amount;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat moment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        record = moment.format(cal.getTime());
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
