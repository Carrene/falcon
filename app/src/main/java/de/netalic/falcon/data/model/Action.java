package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Action {

    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("type")
    private String mType;

    @SerializedName("amount")
    private float mAmount;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("currencySymbol")
    private String mCurencySymbol;

    @SerializedName("walletAddress")
    private String mWalletAddress;


    public int getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public double getAmount() {
        return mAmount;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public String getCurencySymbol() {
        return mCurencySymbol;
    }

    public String getWalletAddress() {
        return mWalletAddress;
    }
}
