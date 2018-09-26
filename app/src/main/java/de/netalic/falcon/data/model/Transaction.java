package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.annotations.PrimaryKey;

public class Transaction implements Parcelable {

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

    @SerializedName("actions")
    private List<Action>mActionList;


    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.mId);
        dest.writeTypedList(this.mActionList);


    }

    protected Transaction(Parcel in){

        mId=in.readInt();
        mActionList = new ArrayList<Action>();
        in.readTypedList(mActionList,Action.CREATOR);



    }
}
