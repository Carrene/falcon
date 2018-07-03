package de.netalic.falcon.model;

public class Currency {

    private int mId;
    private String mName;
    private double mPrice;

    public Currency(int mId, String mName, double mPrice) {
        this.mId = mId;
        this.mName = mName;
        this.mPrice = mPrice;
    }

    public Currency() {

    }

    public int getmId() {

        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmPrice() {
        return mPrice;
    }

    public void setmPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
