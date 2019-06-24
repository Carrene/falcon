package de.netalic.falcon.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity(tableName = "receipt")

public class Receipt implements Parcelable {


    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "Id")
    private int mId;

    @SerializedName("retrievalReferenceNumber")
    @ColumnInfo(name = "RetrievalReferenceNumber")
    private String mRetrievalReferenceNumber;

    @SerializedName("senderWalletAddress")
    @ColumnInfo(name = "SenderWalletAddress")
    private String mSenderWalletAddress;

    @SerializedName("paymentGatewayName")
    @ColumnInfo(name = "PaymentGatewayName")
    private String mPaymentGatewayName;

    @SerializedName("quoteAmount")
    @ColumnInfo(name = "QuoteAmount")
    private double mQuoteAmount;

    @SerializedName("type")
    @ColumnInfo(name = "Type")
    private String mType;

    @SerializedName("baseCurrencyCode")
    @ColumnInfo(name = "BaseCurrencyCode")
    private String mBaseCurrencyCode;

    @SerializedName("failureCause")
    @ColumnInfo(name = "FailureCause")
    private String mFailureCause;

    @SerializedName("quoteCurrencySymbol")
    @ColumnInfo(name = "QuoteCurrencySymbol")
    private String mQuoteCurrencySymbol;

    @SerializedName("createdAt")
    @ColumnInfo(name = "CreatedAt")
    private String mCreatedAt;

    @SerializedName("baseAmount")
    @ColumnInfo(name = "BaseAmount")
    private double mBaseAmount;

    @SerializedName("baseCurrencySymbol")
    @ColumnInfo(name = "BaseCurrencySymbol")
    private String mBaseCurrencySymbol;

    @SerializedName("status")
    @ColumnInfo(name = "Status")
    private String mStatus;

    @SerializedName("paymentGatewayId")
    @ColumnInfo(name = "PaymentGatewayId")
    private int mPaymentGatewayId;

    @SerializedName("quoteCurrencyCode")
    @ColumnInfo(name = "QouteCurrencyCode")
    private String mQouteCurrencyCode;

    @SerializedName("modifiedAt")
    @ColumnInfo(name = "ModifiedAt")
    private String mModifiedAt;

    @SerializedName("recipientWalletAddress")
    @ColumnInfo(name = "RecipientWalletAddress")
    private String mRecipientWalletAddress;

    @SerializedName("senderWalletName")
    @ColumnInfo(name = "SenderWalletName")
    private String mSenderWalletName;

    @SerializedName("recipientWalletName")
    @ColumnInfo(name = "RecipientWalletName")
    private String mRecipientWalletName;


    protected Receipt(Parcel in) {
        mRetrievalReferenceNumber = in.readString();
        mSenderWalletAddress = in.readString();
        mPaymentGatewayName = in.readString();
        mQuoteAmount = in.readDouble();
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
        mSenderWalletName = in.readString();
        mRecipientWalletName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    public Receipt() {

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
        dest.writeString(mSenderWalletName);
        dest.writeString(mRecipientWalletName);
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getRetrievalReferenceNumber() {
        return mRetrievalReferenceNumber;
    }

    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
        mRetrievalReferenceNumber =retrievalReferenceNumber;
    }

    public String getSenderWalletAddress() {
        return mSenderWalletAddress;
    }

    public void setSenderWalletAddress(String senderWalletAddress) {
        mSenderWalletAddress = senderWalletAddress;
    }

    public String getPaymentGatewayName() {
        return mPaymentGatewayName;
    }

    public void setPaymentGatewayName(String paymentGatewayName) {
        mPaymentGatewayName = paymentGatewayName;
    }

    public double getQuoteAmount() {
        return mQuoteAmount;
    }

    public void setQuoteAmount(double quoteAmount) {
        mQuoteAmount = quoteAmount;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }

    public void setBaseCurrencyCode(String baseCurrencyCode) {
        mBaseCurrencyCode = baseCurrencyCode;
    }

    public String getFailureCause() {
        return mFailureCause;
    }

    public void setFailureCause(String failureCause) {
        this.mFailureCause = failureCause;
    }

    public String getQuoteCurrencySymbol() {
        return mQuoteCurrencySymbol;
    }

    public void setQuoteCurrencySymbol(String quoteCurrencySymbol) {
        mQuoteCurrencySymbol = quoteCurrencySymbol;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public double getBaseAmount() {
        return mBaseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        mBaseAmount = baseAmount;
    }

    public String getBaseCurrencySymbol() {
        return mBaseCurrencySymbol;
    }

    public void setBaseCurrencySymbol(String baseCurrencySymbol) {
        mBaseCurrencySymbol = baseCurrencySymbol;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public int getPaymentGatewayId() {
        return mPaymentGatewayId;
    }

    public void setPaymentGatewayId(int paymentGatewayId) {
        mPaymentGatewayId = paymentGatewayId;
    }

    public String getQouteCurrencyCode() {
        return mQouteCurrencyCode;
    }

    public void setQouteCurrencyCode(String qouteCurrencyCode) {
        mQouteCurrencyCode = qouteCurrencyCode;
    }

    public String getModifiedAt() {
        return mModifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        mModifiedAt = modifiedAt;
    }

    public String getRecipientWalletAddress() {
        return mRecipientWalletAddress;
    }

    public void setRecipientWalletAddress(String recipientWalletAddress) {
        mRecipientWalletAddress = recipientWalletAddress;
    }

    public String getSenderWalletName() {
        return mSenderWalletName;
    }

    public void setSenderWalletName(String senderWalletName) {
        mSenderWalletName = senderWalletName;
    }

    public String getRecipientWalletName() {
        return mRecipientWalletName;
    }

    public void setRecipientWalletName(String recipientWalletName) {
        mRecipientWalletName = recipientWalletName;
    }

    public static Creator<Receipt> getCREATOR() {
        return CREATOR;
    }
}