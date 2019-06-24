package de.netalic.falcon.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import de.netalic.falcon.data.model.Receipt;

@Dao
public interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReceipt(Receipt receipt);
}
