package de;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.netalic.falcon.data.dao.UserDao;
import de.netalic.falcon.data.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class InsensitiveDatabase extends RoomDatabase {

    private static InsensitiveDatabase INSTANCE;

    public abstract UserDao userDao();

    public static InsensitiveDatabase getInsensitiveDatabse(Context context) {

        if (INSTANCE == null) {

            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), InsensitiveDatabase.class, "insensitivedatabase")
                    .build();

        }

        return INSTANCE;
    }
}
