package de.netalic.falcon.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import de.netalic.falcon.util.DigestUtil;
import nuesoft.helpdroid.util.Converter;


@Entity(tableName = "authentication")
public class Authentication  {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    private int mId = 1;

    @ColumnInfo(name = "AuthenticationType")
    private int mAuthenticationType;

    @ColumnInfo(name = "AttemptNumber")
    private int mAttemptNumber;

    @ColumnInfo(name = "MaxAttemptNumber")
    private int mMaxAttemptNumber = 5;

    @ColumnInfo(name = "Value")
    private String mCredential;

    public static final int PATTERN_TYPE = 0;
    public static final int PASSWORD_TYPE = 1;


    public Authentication() {

    }

    @Ignore
    public Authentication(String credential, int authenticationType) {
        mCredential = Converter.bytesToHexString(DigestUtil.digestSha512(credential));
        mAuthenticationType = authenticationType;
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setAuthenticationType(int authenticationType) {

        mAuthenticationType = authenticationType;
    }

    public int getAuthenticationType() {

        return mAuthenticationType;
    }


    public int getAttemptNumber() {
        return mAttemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.mAttemptNumber = attemptNumber;
    }


    public int getMaxAttemptNumber() {
        return mMaxAttemptNumber;
    }

    public void setMaxAttemptNumber(int mMaxAttemptNumber) {
        this.mMaxAttemptNumber = mMaxAttemptNumber;
    }

    public boolean isLocked() {

        if (this.mAttemptNumber >= mMaxAttemptNumber) {
            return true;
        }
        return false;
    }

    public void failAttempt() {

        this.mAttemptNumber++;
    }

    public void successAttempt() {

        this.mAttemptNumber = 0;
    }

    public int getRemainAttemptsNumber() {

        return mMaxAttemptNumber - this.mAttemptNumber;
    }

    public String getCredential() {
        return mCredential;
    }

    public void setCredential(String credential) {
        mCredential = credential;
    }
}