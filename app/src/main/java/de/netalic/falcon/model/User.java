package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private int id;
    private String phone;
    private String email;
    private double balance;

    public String getPhone() {

        return phone;
    }

    public String getEmail() {

        return email;
    }

    public double getBalance() {

        return balance;
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
