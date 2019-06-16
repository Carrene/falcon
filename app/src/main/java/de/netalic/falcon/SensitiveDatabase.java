package de.netalic.falcon;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import de.netalic.falcon.data.dao.UserDao;
import de.netalic.falcon.data.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = true)
public abstract class SensitiveDatabase extends RoomDatabase {

    private static SensitiveDatabase INSTANCE;

    public abstract UserDao userDao();

    public static SensitiveDatabase getSensitiveDatabase(Context context) {

        if (INSTANCE == null) {


            Editable editable = new SpannableStringBuilder("12345");
            SafeHelperFactory factory = SafeHelperFactory.fromUser(editable);
            INSTANCE = Room.databaseBuilder(context, SensitiveDatabase.class, "sensitive")
                    .openHelperFactory(factory)
                    .build();

        }
        return INSTANCE;
    }

}
