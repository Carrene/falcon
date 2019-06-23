package de.netalic.falcon.data.model;


public class Contact {


    private String mPhone;

    private String mName;

    private String mPhotoUri;


    public Contact(String phone, String name,String photoUri) {
        mPhone = phone;
        mName = name;
        mPhotoUri=photoUri;
    }

    public Contact() {

    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
