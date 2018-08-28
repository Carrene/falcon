package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

public class Receipt {

    @SerializedName("destinationWalletCurrency")
    private String mDestinationWalletCurrency;

    @SerializedName("payeeId")
    private int mPayeeId;

    @SerializedName("destinationWalletAddress")
    private int mDestinationWalletAddress;

    @SerializedName("amount")
    private double mAmount;

    @SerializedName("sourceWalletAddress")
    private int mSourceWalletAddress;

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

    @SerializedName("sourceWalletCurrency")
    private String mSourceWalletCurrency;

    @SerializedName("paymentGatewayName")
    private String mPaymentGatewayName;

    public String getDestinationWalletCurrency() {
        return mDestinationWalletCurrency;
    }

    public int getPayeeId() {
        return mPayeeId;
    }

    public int getDestinationWalletAddress() {
        return mDestinationWalletAddress;
    }

    public double getAmount() {
        return mAmount;
    }

    public int getSourceWalletAddress() {
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

    public String getSourceWalletCurrency() {
        return mSourceWalletCurrency;
    }

    public String getPaymentGatewayName() {
        return mPaymentGatewayName;
    }
}
