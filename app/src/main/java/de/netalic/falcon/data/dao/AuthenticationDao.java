package de.netalic.falcon.data.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
