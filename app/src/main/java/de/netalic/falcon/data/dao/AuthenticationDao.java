package de.netalic.falcon.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import de.netalic.falcon.data.model.Authentication;

@Dao
public interface AuthenticationDao {


    @Query("SELECT * FROM authentication WHERE Id = :id ")
    Authentication findById(int id);

    @Update
    void updateAuthentication(Authentication authentication);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAuthentication(Authentication authentication);
}
