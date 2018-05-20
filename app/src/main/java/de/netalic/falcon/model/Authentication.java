package de.netalic.falcon.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Authentication extends RealmObject {

    @PrimaryKey
    private int id = 1;

    private int authenticationType;
    public int attemptsNumber;
    private int maxAttemptsNumber = 5;

}