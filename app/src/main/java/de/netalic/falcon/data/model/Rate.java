package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Rate implements Parcelable {

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("buy")
    private double mBuy;

    @SerializedName("sell")
    private double mSell;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("currencySymbol")
    private String mCurrencySymbol;

    @SerializedName("id")
    private int mVerifyRateId;

    public Rate(String usd) {

        mCurrencyCode=usd;
    }

    protected Rate(Parcel in) {
        mCreatedAt = in.readString();
        mBuy = in.readDouble();
        mSell = in.readDouble();
        mCurrencyCode = in.readString();
        mCurrencySymbol = in.readString();
        mVerifyRateId = in.readInt();
    }

    public static final Creator<Rate> CREATOR = new Creator<Rate>() {
        @Override
        public Rate createFromParcel(Parcel in) {
            return new Rate(in);
        }

        @Override
        public Rate[] newArray(int size) {
            return new Rate[size];
        }
    };

    public double getBuy() {
        return mBuy;
    }

    public void setBuy(double buy) {
        this.mBuy = buy;
    }

    public double getSell() {
        return mSell;
    }

    public void setSell(double sell) {
        this.mSell = sell;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }


    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.mCurrencyCode = currencyCode;
    }

    public String getCurrencySymbol() {
        return mCurrencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.mCurrencySymbol = currencySymbol;
    }

    public int getVerifyRateId() {
        return mVerifyRateId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCreatedAt);
        dest.writeDouble(mBuy);
        dest.writeDouble(mSell);
        dest.writeString(mCurrencyCode);
        dest.writeString(mCurrencySymbol);
        dest.writeInt(mVerifyRateId);
    }
}




