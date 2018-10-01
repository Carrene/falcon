package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Receipt extends RealmObject {
    @SerializedName("retrievalReferenceNumber")
    private String mRetrievalReferenceNumber;
    @SerializedName("senderWalletAddress")
    private String mSenderWalletAddress;
    @SerializedName("paymentGatewayName")
    private String mPaymentGatewayName;
    @SerializedName("qouteAmount")
    private int mqQouteAmount;
    @SerializedName("type")
    private String mType;
    @SerializedName("baseCurrencyCode")
    private String mBaseCurrencyCode;
    @SerializedName("failureCause")
    private String mFailureCause;
    @SerializedName("qouteCurrencySymbol")
    private String mQouteCurrencySymbol;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("baseAmount")
    private String mBaseAmount;
    @SerializedName("baseCurrencySymbol")
    private String mBaseCurrencySymbol;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("paymentGatewayId")
    private int mPaymentGatewayId;
    @SerializedName("qouteCurrencyCode")
    private String mQouteCurrencyCode;
    @SerializedName("id")
    private int mId;
    @SerializedName("modifiedAt")
    private String mModifiedAt;
    @SerializedName("recipientWalletAddress")
    private String mRecipientWalletAddress;

    public String getRetrievalReferenceNumber() {
        return mRetrievalReferenceNumber;
    }

    public String getSenderWalletAddress() {
        return mSenderWalletAddress;
    }

    public String getPaymentGatewayName() {
        return mPaymentGatewayName;
    }

    public int getQouteAmount() {
        return mqQouteAmount;
    }

    public String getType() {
        return mType;
    }

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }

    public String getFailureCause() {
        return mFailureCause;
    }

    public String getQouteCurrencySymbol() {
        return mQouteCurrencySymbol;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getBaseAmount() {
        return mBaseAmount;
    }

    public String getBaseCurrencySymbol() {
        return mBaseCurrencySymbol;
    }

    public String getmStatus() {
        return mStatus;
    }

    public int getPaymentGatewayId() {
        return mPaymentGatewayId;
    }

    public String getQouteCurrencyCode() {
        return mQouteCurrencyCode;
    }

    public int getId() {
        return mId;
    }

    public String getModifiedAt() {
        return mModifiedAt;
    }

    public String getRecipientWalletAddress() {
        return mRecipientWalletAddress;
    }
}