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
    @SerializedName("chargeLowerBound")
    private String mChargeLowerBound;
    @SerializedName("mChargeUpperBound")
    private String mChargeUpperBound;


    public Currency(String code) {

        this.mCode = code;
    }

    public int getId() {

        return mId;
    }

    public void setId(int id) {

        this.mId = id;
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

    public String getChargeLowerBound() {

        return mChargeLowerBound;
    }

    public String getChargeUpperBound() {

        return mChargeUpperBound;
    }
}
