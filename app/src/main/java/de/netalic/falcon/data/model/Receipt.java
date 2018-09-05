package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

public class Receipt {

    @SerializedName("destinationWalletCurrencyCode")
    private String mDestinationWalletCurrencyCode;

    @SerializedName("payeeId")
    private int mPayeeId;

    @SerializedName("destinationWalletAddress")
    private String mDestinationWalletAddress;

    @SerializedName("amount")
    private double mAmount;

    @SerializedName("sourceWalletAddress")
    private String mSourceWalletAddress;

    @SerializedName("transactionId")
    private int mTransactionId;

    @SerializedName("transferredAt")
    private String mTransferredAt;

    @SerializedName("type")
    private String mType;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("payerId")
    private int mPayerId;

    @SerializedName("sourceWalletCurrencyCode")
    private String mSourceWalletCurrencyCode;

    @SerializedName("paymentGatewayName")
    private String mPaymentGatewayName;

    @SerializedName(" sourceWalletCurrencySymbol")
    private String  mSourceWalletCurrencySymbol;

    @SerializedName("destinationWalletCurrencySymbol")
    private String mDestinationWalletCurrencySymbol;

    @SerializedName("RRN")
    private String mRRN;

    public String getDestinationWalletCurrencyCode() {
        return mDestinationWalletCurrencyCode;
    }

    public int getPayeeId() {
        return mPayeeId;
    }

    public String getDestinationWalletAddress() {
        return mDestinationWalletAddress;
    }

    public String getRRN() {
        return mRRN;
    }

    public String getDestinationWalletCurrencySymbol() {
        return mDestinationWalletCurrencySymbol;
    }

    public String getSourceWalletCurrencySymbol() {
        return mSourceWalletCurrencySymbol;
    }

    public double getAmount() {
        return mAmount;
    }

    public String getSourceWalletAddress() {
        return mSourceWalletAddress;
    }

    public int getTransactionId() {
        return mTransactionId;
    }

    public String getTransferredAt() {
        return mTransferredAt;
    }

    public String getType() {
        return mType;
    }

    public String getStatus() {
        return mStatus;
    }

    public int getPayerId() {
        return mPayerId;
    }

    public String getSourceWalletCurrencyCode() {
        return mSourceWalletCurrencyCode;
    }

    public String getPaymentGatewayName() {
        return mPaymentGatewayName;
    }
}
