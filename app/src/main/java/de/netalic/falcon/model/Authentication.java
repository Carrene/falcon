package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Authentication extends RealmObject {

    @PrimaryKey
    private int id = 1;
    private int authenticationType;
    private int attemptsNumber;
    private int maxAttemptsNumber = 5;

    public Authentication() {

    }

    public void setAuthenticationType(int authenticationType) {

        this.authenticationType = authenticationType;
    }

    public void setAttemptsNumber(int attemptsNumber) {

        this.attemptsNumber = attemptsNumber;
    }

    public int getAttemptsNumber() {

        return this.attemptsNumber;
    }

    public int getAuthenticationType() {

        return this.authenticationType;
    }

    public int getMaxAttemptsNumber() {

        return this.maxAttemptsNumber;
    }

    public boolean isLocked() {

        if (this.attemptsNumber >= this.maxAttemptsNumber) {
            return true;
        }
        return false;
    }

    public void failAttempt() {

        this.attemptsNumber++;
    }

    public void successAttempt() {

        this.attemptsNumber = 0;
    }
}