package de.netalic.falcon.repository.authentication;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.repository.IRepository;
import io.realm.Realm;

public class AuthenticationRealmRepository implements IRepository<Authentication> {

    private Realm mRealm;


    public AuthenticationRealmRepository() {

        mRealm = Realm.getInstance(MyApp.insensitiveRealmConfiguration.build());

    }

    @Override
    public void update(Authentication authentication) {

        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(authentication);
        mRealm.commitTransaction();
    }

    @Override
    public Authentication get(int id) {

        Authentication authentication = mRealm.where(Authentication.class).equalTo("id", id).findFirst();
        if (authentication == null) {
            return null;
        }
        return mRealm.copyFromRealm(authentication);
    }
}
