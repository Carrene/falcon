package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Wallet {

    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("balance")
    private String mBalance;

    @SerializedName("currency")
    private Currency mCurrency;

    @SerializedName("spendableBalance")
    private double mSpendableBalance;

    public Wallet(int id, String address, Currency currency, double spendableBalance) {
        this.mId = id;
        this.mBalance = address;
        this.mCurrency = currency;
        this.mSpendableBalance = spendableBalance;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getBalance() {
        return mBalance;
    }

    public void setBalance(String balance) {
        this.mBalance = balance;
    }

    public Currency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(Currency currency) {
        this.mCurrency = currency;
    }

    public double getSpendableBalance() {
        return mSpendableBalance;
    }

    public void setSpendableBalance(double spendableBalance) {
        this.mSpendableBalance = spendableBalance;
    }
}
