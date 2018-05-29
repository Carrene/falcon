package de.netalic.falcon.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class Transaction extends RealmObject {

    @PrimaryKey
    @RealmField(name = "Id")
    private int mId;

    @RealmField(name = "PayerId")
    private int mPayerId;

    @RealmField(name = "PayeeId")
    private int mPayeeId;

    @RealmField(name = "Amount")
    private double mAmount;

    @RealmField(name = "Fee")
    private double mFee;

    @RealmField(name = "Rrn")
    private int mRrn;

    @RealmField(name = "CreateAt")
    private Date mCreatedAt;
//    private TransactionStatus transactionStatus;
}
