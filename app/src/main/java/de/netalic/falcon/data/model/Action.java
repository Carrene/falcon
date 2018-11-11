package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Action implements Parcelable {

    @SerializedName("id")
    private int mId;

    @SerializedName("type")
    private String mType;

    @SerializedName("amount")
    private float mAmount;

    @SerializedName("currencyCode")
    private String mCurrencyCode;

    @SerializedName("currencySymbol")
    private String mCurrencySymbol;

    @SerializedName("walletAddress")
    private String mWalletAddress;

    @SerializedName("walletName")
    private String mWalletName;

    @SerializedName("doneAt")
    private boolean mDoneAt;

    @SerializedName("isDone")
    private boolean mIsDone;

    @SerializedName("createdAt")
    private String mCreatedAt;


    private Action(Parcel in) {

        mId = in.readInt();
        mType = in.readString();
        mAmount = in.readFloat();
        mCurrencyCode = in.readString();
        mCurrencySymbol = in.readString();
        mWalletAddress = in.readString();
        mWalletName = in.readString();
        mCreatedAt=in.readString();
        mIsDone=in.readInt()==1;
        mDoneAt=in.readInt()==1;
    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {

            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {

            return new Action[size];
        }
    };

    public int getId() {

        return mId;
    }

    public String getType() {

        return mType;
    }

    public double getAmount() {

        return mAmount;
    }

    public String getCurrencyCode() {

        return mCurrencyCode;
    }

    public String getCurrencySymbol() {

        return mCurrencySymbol;
    }

    public String getWalletAddress() {

        return mWalletAddress;
    }


    public String getWalletName() {

        return mWalletName;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    public boolean isDoneAt() {
        return mDoneAt;
    }

    public boolean isIsDone() {
        return mIsDone;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(mId);
        dest.writeString(mType);
        dest.writeFloat(mAmount);
        dest.writeString(mCurrencyCode);
        dest.writeString(mCurrencySymbol);
        dest.writeString(mWalletAddress);
        dest.writeString(mWalletName);
        dest.writeString(mCreatedAt);
        dest.writeInt(mIsDone?1:0);
        dest.writeInt(mDoneAt?1:0);
    }
}
