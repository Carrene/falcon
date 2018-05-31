package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.netalic.falcon.MyApp;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import nuesoft.helpdroid.device.DeviceUtil;
import nuesoft.helpdroid.util.Converter;

public class User extends RealmObject {

    @PrimaryKey
    @RealmField(name = "Id")
    private int mId;

    @SerializedName("phone")
    @RealmField(name = "Phone")
    private String mPhone;

    @RealmField(name = "Email")
    private String mEmail;

    @RealmField(name = "Balance")
    private double mBalance;

    @RealmField(name = "Udid")
    @SerializedName("udid")
    private String mUdid;

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

        return this.mUdid;
    }

    public String calculateUdid() {

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

    public void setPhone(String phone) {

        this.mPhone = phone;
    }

    public void setEmail(String email) {

        this.mEmail = email;
    }

    public void setBalance(double balance) {

        this.mBalance = balance;
    }
}