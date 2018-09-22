package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Receipt extends RealmObject {


    @SerializedName("transactionId")
    private int mTransactionId;

    @SerializedName("type")
    private String mType;

    @SerializedName("status")
    private String mStatus;

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

    public int getTransactionId() {

        return mTransactionId;
    }

    public String getType() {

        return mType;
    }

    public String getStatus() {

        return mStatus;
    }

//    @SerializedName("destinationWalletCurrency")
//    private String mDestinationWalletCurrency;
//
//    @SerializedName("payeeId")
//    private int mPayeeId;
//
//    @SerializedName("destinationWalletAddress")
//    private String mDestinationWalletAddress;

//    @SerializedName("amount")
//    private double mAmount;
//
//    @SerializedName("sourceWalletAddress")
//    private String mSourceWalletAddress;

//    @SerializedName("transferredAt")
//    private String mTransferredAt;

//    @SerializedName("payerId")
//    private int mPayerId;
//
//    @SerializedName("sourceWalletCurrency")
//    private String mSourceWalletCurrency;
//
//    @SerializedName("paymentGatewayName")
//    private String mPaymentGatewayName;
//
//    @SerializedName(" sourceWalletCurrencySymbol")
//    private String mSourceWalletCurrencySymbol;
//
//    @SerializedName("destinationWalletCurrencySymbol")
//    private String mDestinationWalletCurrencySymbol;
//
//    @SerializedName("RRN")
//    private String mRRN;

}
