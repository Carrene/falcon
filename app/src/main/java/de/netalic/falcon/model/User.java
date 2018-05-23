package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private int id;
    private String phone;
    private String email;
    private double balance;
}
