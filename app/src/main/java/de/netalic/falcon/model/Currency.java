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

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double mPrice) {
        this.mPrice = mPrice;
    }
}
