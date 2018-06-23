package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class ExchangeRate {

    @SerializedName("createdAt")
    @PrimaryKey
    private int mCreatedAt;

    @SerializedName("currency")
    private Currency  mCurrency;

    @SerializedName("buy")
    private double mBuy;

    @SerializedName("sell")
    private double mSell;


    public ExchangeRate(int mCreatedAt, Currency mCurrency, double mBuy, double mSell) {
        this.mCreatedAt = mCreatedAt;
        this.mCurrency = mCurrency;
        this.mBuy = mBuy;
        this.mSell = mSell;
    }

    public int getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(int createdAt) {
        this.mCreatedAt = createdAt;
    }

    public Currency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(Currency currency) {
        this.mCurrency = currency;
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
}




