package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction implements Parcelable {

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
    private List<Action> mActionList;


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

    public String getDate() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = dateFormat.parse(mCreatedAt);
            dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTime() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = dateFormat.parse(mCreatedAt);
            dateFormat = new SimpleDateFormat("h:mm a");
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
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
        dest.writeString(this.mRetrievalReferenceNumber);
        dest.writeString(this.getCreatedAt());
        dest.writeString(this.mPaymentGatewayName);
        dest.writeString(this.mBraintreeToken);
    }

    protected Transaction(Parcel in) {

        mId = in.readInt();
        mActionList = new ArrayList<Action>();
        in.readTypedList(mActionList, Action.CREATOR);
        mRetrievalReferenceNumber = in.readString();
        mCreatedAt = in.readString();
        mPaymentGatewayName=in.readString();
        mBraintreeToken=in.readString();
    }
}
