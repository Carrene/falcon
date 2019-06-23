package de.netalic.falcon.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import de.netalic.falcon.data.model.Receipt;

@Dao
public interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReceipt(Receipt receipt);
}
