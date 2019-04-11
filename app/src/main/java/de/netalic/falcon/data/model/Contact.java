package de.netalic.falcon.data.model;

import io.realm.annotations.PrimaryKey;

public class Contact {

    @PrimaryKey
    private String mPhone;


    private String mName;


    public Contact(String phone, String name) {
        mPhone = phone;
        mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setmPhone(String phone) {
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = mName;
    }
}
