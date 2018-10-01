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

    @SerializedName("quoteAmount")
    private double mqQuoteAmount;

    @SerializedName("type")
    private String mType;

    @SerializedName("baseCurrencyCode")
    private String mBaseCurrencyCode;

    @SerializedName("failureCause")
    private String mFailureCause;

    @SerializedName("quoteCurrencySymbol")
    private String mQuoteCurrencySymbol;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("baseAmount")
    private double mBaseAmount;

    @SerializedName("baseCurrencySymbol")
    private String mBaseCurrencySymbol;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("paymentGatewayId")
    private int mPaymentGatewayId;

    @SerializedName("quoteCurrencyCode")
    private String mQouteCurrencyCode;

    @SerializedName("id")
    private int mId;

    @SerializedName("modifiedAt")
    private String mModifiedAt;

    @SerializedName("recipientWalletAddress")
    private String mRecipientWalletAddress;

    @SerializedName("senderWalletName")
    private String mSenderWalletName;

    @SerializedName("recipientWalletName")
    private String mRecipientWalletName;

    public String getSenderWalletName() {

        return mSenderWalletName;
    }

    public String getRecipientWalletName() {

        return mRecipientWalletName;
    }

    public String getRetrievalReferenceNumber() {

        return mRetrievalReferenceNumber;
    }

    public String getSenderWalletAddress() {

        return mSenderWalletAddress;
    }

    public String getPaymentGatewayName() {

        return mPaymentGatewayName;
    }

    public double getQouteAmount() {

        return mqQuoteAmount;
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

    public String getQuoteCurrencySymbol() {

        return mQuoteCurrencySymbol;
    }

    public String getCreatedAt() {

        return mCreatedAt;
    }

    public double getBaseAmount() {

        return mBaseAmount;
    }

    public String getBaseCurrencySymbol() {

        return mBaseCurrencySymbol;
    }

    public String getStatus() {

        return mStatus;
    }

    public int getPaymentGatewayId() {

        return mPaymentGatewayId;
    }

    public String getQuoteCurrencyCode() {

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