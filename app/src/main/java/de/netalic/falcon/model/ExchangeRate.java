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
    private String mBuy;

    @SerializedName("sell")
    private String mSell;

    @SerializedName("currencyId")
    private int mCurrencyID;

    public ExchangeRate(int createdAt, Currency currency, String buy, String sell, int currencyID) {
        this.mCreatedAt = createdAt;
        this.mCurrency = currency;
        this.mBuy = buy;
        this.mSell = sell;
        this.mCurrencyID = currencyID;
    }

    public ExchangeRate(Currency currency) {

        mCurrency=currency;

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

    public String getBuy() {
        return mBuy;
    }

    public void setBuy(String buy) {
        this.mBuy = buy;
    }

    public String getSell() {
        return mSell;
    }

    public void setSell(String sell) {
        this.mSell = sell;
    }

    public int getCurrencyID() {
        return mCurrencyID;
    }

    public void setCurrencyID(int currencyID) {
        this.mCurrencyID = currencyID;
    }
}




