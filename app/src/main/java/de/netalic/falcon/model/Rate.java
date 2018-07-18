package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Rate {

    @SerializedName("createdAt")
    @PrimaryKey
    private String mCreatedAt;

    @SerializedName("currency")
    private Currency mCurrency;

    @SerializedName("buy")
    private String mBuy;

    @SerializedName("sell")
    private String mSell;

    @SerializedName("currencyId")
    private int mCurrencyID;

    public Rate(Currency currency) {

        mCurrency = currency;
    }

    public Currency getCurrency() {

        return mCurrency;
    }

    public String getSell() {

        return mSell;
    }

}




