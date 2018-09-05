package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmModel;

public class TransferReceipt{

    @SerializedName("destinationWalletCurrency")
    private String mDestinationWalletCurrency;

    @SerializedName("payeeId")
    private int mPayeeId;

    @SerializedName("destinationWalletAddress")
    private String mDestinationWalletAddress;

    @SerializedName("amount")
    private double mAmount;

    @SerializedName("sourceWalletAddress")
    private String mSourceWalletAddress;
}
