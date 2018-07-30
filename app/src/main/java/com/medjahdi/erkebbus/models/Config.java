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
    private String lineNumber;
    private String vid;
    private String pid;
    private float minimumAmountToCompost, compostAmount;

    public Config(String busId, String lineNumber, String vid, String pid, float minimumAmountToCompost, float compostAmount) {
        this.busId = busId;
        this.lineNumber = lineNumber;
        this.vid = vid;
        this.pid = pid;
        this.minimumAmountToCompost = minimumAmountToCompost;
        this.compostAmount = compostAmount;
    }

    public Config(String busId, String lineNumber, String vid, float minimumAmountToCompost, float compostAmount) {
        this.busId = busId;
        this.lineNumber = lineNumber;
        this.vid = vid;
        this.minimumAmountToCompost = minimumAmountToCompost;
        this.compostAmount = compostAmount;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
