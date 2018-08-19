package de.netalic.falcon.repository.user;

import java.util.List;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;
import io.realm.Realm;

public class UserRealmRepository implements IUserRepository {

    private Realm mRealm;

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
    public void update(User user, CallRepository<User> callRepository) {

        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(user);
        mRealm.commitTransaction();
        mRealm.close();
        callRepository.onDone(new Deal<>(user, null, null));
    }

    @Override
    public void get(Integer identifier, CallRepository<User> callRepository) {

        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
        User user = mRealm.where(User.class).findFirst();
        Deal deal;
        if (user == null) {
            deal = new Deal<>(null, null, null);
        } else {
            User returnUser = mRealm.copyFromRealm(user);
            deal = new Deal<>(returnUser, null, null);
        }
        mRealm.close();
        callRepository.onDone(deal);
    }

    @Override
    public void getAll(CallRepository<List<User>> callRepository) {

        throw new UnsupportedOperationException();
    }
}
