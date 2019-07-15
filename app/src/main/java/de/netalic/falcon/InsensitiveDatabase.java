package de.netalic.falcon;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import de.netalic.falcon.data.dao.AuthenticationDao;
import de.netalic.falcon.data.model.Authentication;


@Database(entities = {Authentication.class}, version = 1, exportSchema = true)
public abstract class InsensitiveDatabase extends RoomDatabase {

    private static InsensitiveDatabase INSTANCE;

    public abstract AuthenticationDao authenticationDao();


    public void close() {
        super.close();

        INSTANCE = null;
    }


    public static InsensitiveDatabase getInsensitiveDatabase(Context context) {
        if (INSTANCE == null) {
            Editable editable = new SpannableStringBuilder("1234");
            SafeHelperFactory factory = SafeHelperFactory.fromUser(editable);
            INSTANCE = Room.databaseBuilder(context, InsensitiveDatabase.class, "insensitive.db")
                    .openHelperFactory(factory)
                    .build();
        }
        return INSTANCE;
    }
}
