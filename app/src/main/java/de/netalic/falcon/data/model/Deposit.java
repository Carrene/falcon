package de.netalic.falcon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deposit implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("buyRate")
    private double mRate;

    @SerializedName("paidAmount")
    private double mPaidAmount;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("chargedAmount")
    private double mChargeAmount;

    @SerializedName("walletName")
    private String mWalletName;

    @SerializedName("walletCurrencyCode")
    private String mWalletCurrencyCode;

    @SerializedName("retrievalReferenceNumber")
    private String mRetrievalReferenceNumber;

    @SerializedName("paymentGatewayCurrencyCode")
    private String mPaymentGatewayCurrencyCode;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("paymentGatewayName")
    private String mPaymentGatewayName;

    @SerializedName("modifiedAt")
    private String mModifiedAt;

    @SerializedName("isExpired")
    private boolean mIsExpired;

    @SerializedName("braintreeToken")
    private String mBraintreeToken;

    @SerializedName("walletId")
    private int mWalletId;

    @SerializedName("transactionId")
    private int mTransactionId;

    public int getId() {

        return mId;
    }

    public int getWalletId() {

        return mWalletId;
    }

    public double getRate() {

        return mRate;
    }

    public double getPaidAmount() {

        return mPaidAmount;
    }

    public String getCreatedAt() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = dateFormat.parse(mCreatedAt);
            dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public double getChargeAmount() {

        return mChargeAmount;
    }

    public String getWalletName() {

        return mWalletName;
    }

    public String getWalletCurrencyCode() {

        return mWalletCurrencyCode;
    }

    public String getRetrievalReferenceNumber() {

        return mRetrievalReferenceNumber;
    }

    public String getPaymentGatewayCurrencyCode() {

        return mPaymentGatewayCurrencyCode;
    }

    public String getStatus() {

        return mStatus;
    }

    public String getPaymentGatewayName() {

        return mPaymentGatewayName;
    }


    public int getTransactionId() {
        return mTransactionId;
    }

    public void setTransactionId(int transactionId) {
        this.mTransactionId = transactionId;
    }

    public String getModifiedAt() {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = dateFormat.parse(mModifiedAt);
            dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isIsExpired() {

        return mIsExpired;
    }

    public String getBraintreeToken() {

        return mBraintreeToken;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeDouble(mPaidAmount);
        dest.writeDouble(mChargeAmount);
        dest.writeString(mWalletName);
        dest.writeInt(mWalletId);
        dest.writeString(mPaymentGatewayName);
        dest.writeString(mBraintreeToken);
        dest.writeInt(mId);
        dest.writeString(mRetrievalReferenceNumber);
        dest.writeString(mModifiedAt);
        dest.writeInt(mTransactionId);

    }

    private Deposit(Parcel in) {

        mPaidAmount = in.readDouble();
        mChargeAmount = in.readDouble();
        mWalletName = in.readString();
        mWalletId = in.readInt();
        mPaymentGatewayName = in.readString();
        mBraintreeToken = in.readString();
        mId = in.readInt();
        mRetrievalReferenceNumber = in.readString();
        mModifiedAt = in.readString();
        mTransactionId=in.readInt();
    }

    public static final Creator<Deposit> CREATOR = new Creator<Deposit>() {
        @Override
        public Deposit createFromParcel(Parcel in) {

            return new Deposit(in);
        }

        @Override
        public Deposit[] newArray(int size) {

            return new Deposit[size];
        }
    };
}
