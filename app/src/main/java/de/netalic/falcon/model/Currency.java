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

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        this.mPrice = price;
    }
}
