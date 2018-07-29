package de.netalic.falcon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Deposit implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("rate")
    private double mRate;

    @SerializedName("paidAmount")
    private double mPaidAmount;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("chargeAmount")
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

        return mCreatedAt;
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

    public String getModifiedAt() {

        return mModifiedAt;
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
