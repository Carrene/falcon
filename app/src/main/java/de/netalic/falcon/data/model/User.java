package de.netalic.falcon.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import de.netalic.falcon.MyApp;
import nuesoft.helpdroid.device.DeviceUtil;
import nuesoft.helpdroid.util.Converter;

@Entity(tableName = "user")
public class User implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "Id")
    private int mId;

    @SerializedName("phone")
    @ColumnInfo(name = "Phone")
    private String mPhone;

    @ColumnInfo(name = "Email")
    @SerializedName("email")
    private String mEmail;


    @ColumnInfo(name = "Balance")
    private double mBalance;

    @android.arch.persistence.room.Ignore
    @SerializedName("udid")
    private String mUdid;

    @SerializedName("deviceName")
    private String mDeviceName;

    @android.arch.persistence.room.Ignore
    @SerializedName("wallets")
    private List<Wallet> mWallets;

    @android.arch.persistence.room.Ignore
    @SerializedName("isActive")
    boolean mIsActive;

    @ColumnInfo(name = "Secret")
    @SerializedName("secret")
    private String mSecret;

    @ColumnInfo(name = "HmacSecret")
    @SerializedName("hmacSecret")
    private String mHmacSecret;

    @Ignore
    @SerializedName("isNewClient")
    boolean isNewClient;

    @SerializedName("baseCurrencySymbol")
    private String mBaseCurrencySymbol;

    @ColumnInfo(name = "baseCurrencyCode")
    @SerializedName("baseCurrencyCode")
    private String mBaseCurrencyCode;

    @android.arch.persistence.room.Ignore
    String mActivationCode;

    public User() {

    }


    @Ignore
    public User(String phone) {

        mPhone = phone;
    }

    @Ignore
    public User(String phone, String udid, String activationCode) {

        mPhone = phone;
        mUdid = udid;
        mActivationCode = activationCode;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getSecret() {

        return mSecret;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public void setHmacSecret(String hmacSecret) {
        mHmacSecret = hmacSecret;
    }

    public String getHmacSecret() {

        return mHmacSecret;
    }

    public void setActivationCode(String activationCode) {

        this.mActivationCode = activationCode;
    }

    public String getActivationCode() {

        return mActivationCode;
    }

    public String getPhone() {

        return mPhone;
    }


    public void setBaseCurrencyCode(String baseCurrencyCode) {
        this.mBaseCurrencyCode = baseCurrencyCode;
    }

    public String getEmail() {

        return mEmail;
    }

    public double getBalance() {

        return mBalance;
    }

    public String getUdid() {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(DeviceUtil.getSecureId(MyApp.getInstance()).getBytes("UTF-8"));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest();
        String digestSecureId = Converter.bytesToHexString(digest);
        return digestSecureId;
    }

    public String getDeviceName() {

        return this.mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        this.mDeviceName = deviceName;
    }

    public String calculateDeviceName() {

        mDeviceName = DeviceUtil.getDeviceName();
        return mDeviceName;
    }

    public void setPhone(String phone) {

        this.mPhone = phone;
    }

    public void setEmail(String email) {

        this.mEmail = email;
    }

    public void setBalance(double balance) {

        this.mBalance = balance;
    }

    public List<Wallet> getWallets() {

        return mWallets;
    }

    public boolean getIsActive() {
        return mIsActive;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mPhone);
        dest.writeString(this.mEmail);

    }

    protected User(Parcel in) {

        mPhone = in.readString();
        mEmail = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {

            return new User(in);
        }

        @Override
        public User[] newArray(int size) {

            return new User[size];
        }
    };

    public String getBaseCurrencySymbol() {
        return mBaseCurrencySymbol;
    }

    public void setBaseCurrencySymbol(String baseCurrencySymbol) {
        this.mBaseCurrencySymbol = baseCurrencySymbol;
    }

    public String getBaseCurrencyCode() {
        return mBaseCurrencyCode;
    }
}
