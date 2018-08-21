package de.netalic.falcon.model;

public class State {

    //status = success
    //mId = status
    //mKey = success

    private String mId;
    private String mKey;
    private String mValue;
    private String mUiKey;

    public State(String id, String key, String value, String uiKey) {

        mId = id;
        mKey = key;
        mValue = value;
        mUiKey = uiKey;
    }


    public String getId() {

        return mId;
    }

    public String getKey() {

        return mKey;
    }

    public String getValue() {

        return mValue;
    }

    public String getUiKey() {

        return mUiKey;
    }
}
