package de.netalic.falcon.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Authentication extends RealmObject {

    @PrimaryKey
    @RealmField(name = "Id")
    private int mId = 1;
    @RealmField(name = "AuthenticationType")
    private int mAuthenticationType;
    @RealmField(name = "AttemptNumber")
    private int mAttemptNumber;
    @RealmField(name = "MaxAttemptNumber")
    private int mMaxAttemptNumber = 5;
    @RealmField(name = "Value")
    private String mCredential;

    public static int sPatternType=0;
    public static int sPasswordType=1;

    public Authentication() {

    }

    public Authentication(String credential, int authenticationType) {

        this.mCredential = credential;
        this.mAuthenticationType = authenticationType;
    }

    public void setAuthenticationType(int authenticationType) {

        this.mAuthenticationType = authenticationType;
    }

    public void setAttemptsNumber(int attemptsNumber) {

        this.mAttemptNumber = attemptsNumber;
    }

    public int getAttemptsNumber() {

        return this.mAttemptNumber;
    }

    public int getAuthenticationType() {

        return this.mAuthenticationType;
    }

    public int getMaxAttemptsNumber() {

        return this.mMaxAttemptNumber;
    }

    public boolean isLocked() {

        if (this.mAttemptNumber >= this.mMaxAttemptNumber) {
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

        return mMaxAttemptNumber - mAttemptNumber;
    }

    public String getCredential() {
        return mCredential;
    }

    public void setCredential(String credential) {
        this.mCredential = credential;
    }
}