package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Rate {

    @SerializedName("createdAt")
    @PrimaryKey
    private String mCreatedAt;

    @SerializedName("buy")
    private double mBuy;

    @SerializedName("sell")
    private double mSell;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("currencySymbol")
    private String mCurrencySymbol;

    public Rate(String usd) {

        mCurrencyCode=usd;
    }

    public double getBuy() {
        return mBuy;
    }

    public void setBuy(double buy) {
        this.mBuy = buy;
    }

    public double getSell() {
        return mSell;
    }

    public void setSell(double sell) {
        this.mSell = sell;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }


    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.mCurrencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.mCurrencySymbol = currencySymbol;
    }
}




