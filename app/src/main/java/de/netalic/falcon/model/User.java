package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.netalic.falcon.MyApp;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import nuesoft.helpdroid.device.DeviceUtil;
import nuesoft.helpdroid.util.Converter;

public class User extends RealmObject {

    @PrimaryKey
    private int id;
    @SerializedName("phone")
    private String phone;
    private String email;
    private double balance;
    @SerializedName("udid")
    private String udid;

    public String getPhone() {

        return phone;
    }

    public String getEmail() {

        return email;
    }

    public double getBalance() {

        return balance;
    }

    public String getUdid() {

        return this.udid;
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

        this.phone = phone;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public void setBalance(double balance) {

        this.balance = balance;
    }
}
