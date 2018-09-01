package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Wallet implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    private int mId;

    @SerializedName("balance")
    private double mBalance;

    @SerializedName("currency")
    private Currency mCurrency;

    @SerializedName("spendableBalance")
    private double mSpendableBalance;

    @SerializedName("name")
    private String mName;

    public Wallet(int id, double balance, Currency currency, double spendableBalance) {

        this.mId = id;
        this.mBalance = balance;
        this.mCurrency = currency;
        this.mSpendableBalance = spendableBalance;
    }

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

    public Currency getCurrency() {

        return mCurrency;
    }

    public void setCurrency(Currency currency) {

        this.mCurrency = currency;
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

    }

    protected Wallet(Parcel in) {

        mName=in.readString();
        mBalance=in.readDouble();
        mId=in.readInt();

    }
}
