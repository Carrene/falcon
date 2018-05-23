package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Wallet extends RealmObject {

    @PrimaryKey
    private int id;
    private String address;
    private User user;
    private String balance;
//    private WalletStatus walletStatus;

}
