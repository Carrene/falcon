package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Rate {

    @SerializedName("createdAt")
    @PrimaryKey
    private String mCreatedAt;

    @SerializedName("buy")
    private String mBuy;

    @SerializedName("sell")
    private String mSell;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    public Rate(String usd) {

        mCurrencyCode=usd;
    }


    public String getSell() {

        return mSell;
    }

    public String getBuy() {

        return mBuy;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }



    public void setBuy(String buy) {
        this.mBuy = buy;
    }



    public void setSell(String sell) {
        this.mSell = sell;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.mCurrencyCode = currencyCode;
    }
}




