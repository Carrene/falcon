package de.netalic.falcon.data.repository.user;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import de.netalic.falcon.SensitiveDatabase;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.Deal;


public class UserRealmRepository implements IUserRepository {

    private SensitiveDatabase mSensitiveDatabase;
    private Context mContext;

    public UserRealmRepository(Context context) {
        mContext = context;
    }

    @Override
    public void bind(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void claim(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void setEmail(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void insert(User user, CallRepository<User> callRepository) {


    }

    @Override
    public void updateCurrency(int userId, String baseCurrencyCode, CallRepository<User> callRepository) {

    }

    @Override
    public void update(User user, CallRepository<User> callRepository) {

        AsyncTask.execute(() -> {

            mSensitiveDatabase = SensitiveDatabase.getSensitiveDatabase(mContext);
            mSensitiveDatabase.userDao().insert(user);
            mSensitiveDatabase.close();
            callRepository.onDone(new Deal<>(user, null, null));

        });
    }

    @Override
    public void get(Integer identifier, CallRepository<User> callRepository) {

        AsyncTask.execute(() -> {
            Deal deal;
            mSensitiveDatabase = SensitiveDatabase.getSensitiveDatabase(mContext);
            User user = mSensitiveDatabase.userDao().findById(identifier);
            mSensitiveDatabase.close();
            if (user == null) {
                deal = new Deal<>(null, null, null);

            } else {

                deal = new Deal<>(user, null, null);

            }
            callRepository.onDone(deal);

        });
    }

    @Override
    public void getAll(CallRepository<List<User>> callRepository) {

        throw new UnsupportedOperationException();
    }
}
