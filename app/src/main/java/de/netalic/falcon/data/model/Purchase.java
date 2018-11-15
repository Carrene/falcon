package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

public class Purchase {

    @SerializedName("amount")
    private float mAmount;

    @SerializedName("walletAddress")
    private String mWalletAddress;

    @SerializedName("currencyCode")
    private String mCodeCurrency;


    public Purchase(float amount, String walletAddress, String codeCurrency) {
        mAmount = amount;
        mWalletAddress = walletAddress;
        mCodeCurrency = codeCurrency;
    }

    public Purchase(float amount, String walletAddress) {
        mAmount = amount;
        mWalletAddress = walletAddress;

    }

    public Purchase(String walletAddress) {

        mWalletAddress = walletAddress;

    }

    public float getAmount() {
        return mAmount;
    }

    public String getWalletAddress() {
        return mWalletAddress;
    }

    public String getCodeCurrency() {
        return mCodeCurrency;
    }
}
