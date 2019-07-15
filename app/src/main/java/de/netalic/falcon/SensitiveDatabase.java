package de.netalic.falcon;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import de.netalic.falcon.data.dao.ReceiptDao;
import de.netalic.falcon.data.dao.UserDao;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.model.User;

@Database(entities = {User.class, Receipt.class}, version = 1, exportSchema = true)
public abstract class SensitiveDatabase extends RoomDatabase {

    private static SensitiveDatabase INSTANCE;

    public abstract UserDao userDao();
    public abstract ReceiptDao receiptDao();

    public void close() {
        super.close();

        INSTANCE = null;
    }

    public static SensitiveDatabase getSensitiveDatabase(Context context) {

        if (INSTANCE == null) {


            Editable editable = new SpannableStringBuilder("1234");
            SafeHelperFactory factory = SafeHelperFactory.fromUser(editable);
            INSTANCE = Room.databaseBuilder(context, SensitiveDatabase.class, "sensitive.db")
                    .openHelperFactory(factory)
                    .build();

        }
        return INSTANCE;
    }

}
