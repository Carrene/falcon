package de.netalic.falcon.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Transaction extends RealmObject {

    @PrimaryKey
    private int id;
    private int payerId;
    private int payeeId;
    private double amount;
    private double fee;
    private int rrn;
    private Date createdAt;
//    private TransactionStatus transactionStatus;
}
