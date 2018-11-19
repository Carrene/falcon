package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wallet implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("balance")
    private double mBalance;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("spendableBalance")
    private double mSpendableBalance;

    @SerializedName("name")
    private String mName;

    @SerializedName("createdAt")
    private String mCreatedAt;

    @SerializedName("ownerId")
    private int mOwnerId;

    @SerializedName("modifiedAt")
    private String mModifiedAt;

    @SerializedName("address")
    private String mAddress;

    @SerializedName("currencySymbol")
    private String mCurrencySymbol;

    public Wallet() {

    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

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

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
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

    public void setModifiedAt(String modifiedAt) {
        this.mModifiedAt = modifiedAt;
    }

    public int getOwnerId() {
        return mOwnerId;
    }

    public void setOwnerId(int ownerId) {
        this.mOwnerId = ownerId;
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

    public void setCreatedAt(String createdAt) {
        this.mCreatedAt = createdAt;
    }

    public int getId() {

        return mId;
    }

    public void setId(int id) {

        this.mId = id;
    }

    public double getBalance() {

        return mBalance;
    }

    public String getName() {

        return mName;
    }


    public double getSpendableBalance() {

        return mSpendableBalance;
    }

    public void setSpendableBalance(double spendableBalance) {

        this.mSpendableBalance = spendableBalance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mName);
        dest.writeDouble(this.mBalance);
        dest.writeInt(this.mId);
        dest.writeString(this.mCurrencyCode);
        dest.writeString(this.mAddress);


    }

    protected Wallet(Parcel in) {

        mName=in.readString();
        mBalance=in.readDouble();
        mId=in.readInt();
        mCurrencyCode=in.readString();
        mAddress=in.readString();
    }
}
