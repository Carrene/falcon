package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

public class Purchase {

    @SerializedName("amount")
    private double mAmount;

    @SerializedName("walletAddress")
    private int mWalletAddress;

    @SerializedName("codeCurrency")
    private String mCodeCurrency;


    public Purchase(double amount, int walletAddress, String codeCurrency) {
        mAmount = amount;
        mWalletAddress = walletAddress;
        mCodeCurrency = codeCurrency;
    }

    public double getAmount() {
        return mAmount;
    }

    public int getWalletAddress() {
        return mWalletAddress;
    }

    public String getCodeCurrency() {
        return mCodeCurrency;
    }
}
