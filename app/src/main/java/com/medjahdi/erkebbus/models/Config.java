package com.medjahdi.erkebbus.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.medjahdi.erkebbus.R;
import com.medjahdi.erkebbus.dal.DatabaseHandler;

import java.util.ArrayList;

public class Config {
    private String busId;
    private float minimumAmountToCompost, compostAmount;

    public Config(String busId, float minimumAmountToCompost, float compostAmount) {

        this.busId = busId;
        this.minimumAmountToCompost = minimumAmountToCompost;
        this.compostAmount = compostAmount;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public double getMinimumAmountToCompost() {
        return minimumAmountToCompost;
    }

    public void setMinimumAmountToCompost(float minimumAmountToCompost) {
        this.minimumAmountToCompost = minimumAmountToCompost;
    }

    public float getCompostAmount() {
        return compostAmount;
    }

    public void setCompostAmount(float compostAmount) {
        this.compostAmount = compostAmount;
    }


}
