package com.medjahdi.erkebbus.models;

public class Config {
    private String busId;
    private float minimumAmountToCompost;

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

    public Config(String busId, float minimumAmountToCompost, float compostAmount) {

        this.busId = busId;
        this.minimumAmountToCompost = minimumAmountToCompost;
        this.compostAmount = compostAmount;
    }

    private float compostAmount;
}
