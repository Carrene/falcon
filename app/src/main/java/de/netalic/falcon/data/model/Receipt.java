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

    @SerializedName("quoteAmount")
    private double mQuoteAmount;

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


    public Receipt() {

    }


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


    public double getQuoteAmount() {

        return mQuoteAmount;
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
        mQuoteAmount = in.readInt();
        mType = in.readString();
        mBaseCurrencyCode = in.readString();
        mFailureCause = in.readString();
        mQuoteCurrencySymbol = in.readString();
        mCreatedAt = in.readString();
        mBaseAmount = in.readDouble();
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
        dest.writeDouble(mQuoteAmount);
        dest.writeString(mType);
        dest.writeString(mBaseCurrencyCode);
        dest.writeString(mFailureCause);
        dest.writeString(mQuoteCurrencySymbol);
        dest.writeString(mCreatedAt);
        dest.writeDouble(mBaseAmount);
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