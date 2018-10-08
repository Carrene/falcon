package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

public class Purchase {

    @SerializedName("amount")
    private float mAmount;

    @SerializedName("walletAddress")
    private String mWalletAddress;

    @SerializedName("codeCurrency")
    private String mCodeCurrency;


    public Purchase(float amount, String walletAddress, String codeCurrency) {
        mAmount = amount;
        mWalletAddress = walletAddress;
        mCodeCurrency = codeCurrency;
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
