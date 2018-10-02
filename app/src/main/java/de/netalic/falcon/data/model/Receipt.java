package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Receipt extends RealmObject implements Parcelable {

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

    public Receipt() {

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

    public static final Creator<Receipt> CREATOR = new Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {

            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {

            return new Receipt[size];
        }
    };

    protected Receipt(Parcel in) {

        mRetrievalReferenceNumber = in.readString();
        mSenderWalletAddress = in.readString();
        mPaymentGatewayName = in.readString();
        mqQouteAmount = in.readInt();
        mType = in.readString();
        mBaseCurrencyCode = in.readString();
        mFailureCause = in.readString();
        mQouteCurrencySymbol = in.readString();
        mCreatedAt = in.readString();
        mBaseAmount = in.readString();
        mBaseCurrencySymbol = in.readString();
        mStatus = in.readString();
        mPaymentGatewayId = in.readInt();
        mQouteCurrencyCode = in.readString();
        mId = in.readInt();
        mModifiedAt = in.readString();
        mRecipientWalletAddress = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mRetrievalReferenceNumber);
        dest.writeString(mSenderWalletAddress);
        dest.writeString(mPaymentGatewayName);
        dest.writeInt(mqQouteAmount);
        dest.writeString(mType);
        dest.writeString(mBaseCurrencyCode);
        dest.writeString(mFailureCause);
        dest.writeString(mQouteCurrencySymbol);
        dest.writeString(mCreatedAt);
        dest.writeString(mBaseAmount);
        dest.writeString(mBaseCurrencySymbol);
        dest.writeString(mStatus);
        dest.writeInt(mPaymentGatewayId);
        dest.writeString(mQouteCurrencyCode);
        dest.writeInt(mId);
        dest.writeString(mModifiedAt);
        dest.writeString(mRecipientWalletAddress);
    }

    @Override
    public int describeContents() {

        return 0;
    }
}