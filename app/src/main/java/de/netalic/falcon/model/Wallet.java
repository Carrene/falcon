package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Wallet extends RealmObject {

    @PrimaryKey
    @RealmField(name = "Id")
    @SerializedName("id")
    private int mId;

    @SerializedName("balance")
    private String mAddress;

    @SerializedName("currency")
    private Currency mCurrency;

    @SerializedName("spendableBalance")
    private double mSpendableBalance;

    public Wallet(int id, String address, Currency currency, double spendableBalance) {
        this.mId = id;
        this.mAddress = address;
        this.mCurrency = currency;
        this.mSpendableBalance = spendableBalance;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
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
