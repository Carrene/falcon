package de.netalic.falcon.model;

import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Currency {

    @PrimaryKey
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("code")
    private String mCode;

    public Currency(int id, String name, String code) {
        this.mId = id;
        this.mName = name;
        this.mCode = code;
    }

    public Currency() {

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId =id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }
}
