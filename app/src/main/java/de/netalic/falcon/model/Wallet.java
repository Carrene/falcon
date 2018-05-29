package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Wallet extends RealmObject {

    @PrimaryKey
    @RealmField(name = "Id")
    private int mId;

    @RealmField(name = "Address")
    private String mAddress;

    @RealmField(name = "User")
    private User mUser;

    @RealmField(name = "Balance")
    private String mBalance;

//    private WalletStatus walletStatus;

}
