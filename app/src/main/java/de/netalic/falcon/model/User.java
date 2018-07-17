package de.netalic.falcon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.netalic.falcon.MyApp;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import nuesoft.helpdroid.device.DeviceUtil;
import nuesoft.helpdroid.util.Converter;

public class User extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @RealmField(name = "Id")
    private int mId;

    @SerializedName("phone")
    @RealmField(name = "Phone")
    private String mPhone;

    @RealmField(name = "Email")
    @SerializedName("email")
    private String mEmail;

    @RealmField(name = "Balance")
    private double mBalance;

    @Ignore
    @SerializedName("udid")
    private String mUdid;

    @SerializedName("deviceName")
    private String mDeviceName;

    @Ignore
    @SerializedName("isActive")
    boolean mIsActive;

    @SerializedName("secret")
    String mSecret;

    @SerializedName("hmacSecret")
    String mHmacSecret;

    @SerializedName("token")
    String mToken;

    @SerializedName("isNewClient")
    boolean isNewClient;

    @Ignore
    String mActivationCode;

    public User() {

    }

    public User(String phone) {

        mPhone = phone;
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

    public String calculateDeviceName() {

        return DeviceUtil.getDeviceName();
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
}
