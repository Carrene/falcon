package de.netalic.falcon.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.annotations.PrimaryKey;

public class Transaction {

    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("type")
    private String mType;

    @SerializedName("rollbackTransactionId")
    private int mRollbackTransactionId;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("modifiedAt")
    private String mModifiedAt;

    @SerializedName("retrievalReferenceNumber")
    private String mRetrievalReferenceNumber;

    @SerializedName("paymentGatewayId")
    private int mPaymentGatewayId;

    @SerializedName("failureCause")
    private String mFailureCause;

    @SerializedName("paymentGatewayName")
    private String mPaymentGatewayName;

    @SerializedName("braintreeToken")
    private String mBraintreeToken;

    @SerializedName("action")
    private List<Action>mActionList;


    public int getId() {
        return mId;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getType() {
        return mType;
    }

    public int getRollbackTransactionId() {
        return mRollbackTransactionId;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getModifiedAt() {
        return mModifiedAt;
    }

    public String getRetrievalReferenceNumber() {
        return mRetrievalReferenceNumber;
    }

    public int getPaymentGatewayId() {
        return mPaymentGatewayId;
    }

    public String getFailureCause() {
        return mFailureCause;
    }

    public String getPaymentGatewayName() {
        return mPaymentGatewayName;
    }

    public String getBraintreeToken() {
        return mBraintreeToken;
    }

    public List<Action> getActionList() {
        return mActionList;
    }
}
