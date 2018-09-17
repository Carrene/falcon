package de.netalic.falcon.data.repository.authentication;

import java.util.List;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.base.Deal;
import de.netalic.falcon.data.repository.base.IRepository;
import io.realm.Realm;
import nuesoft.helpdroid.util.Converter;

public class AuthenticationRealmRepository implements IAuthenticationRepository {

    private Realm mRealm;

    public AuthenticationRealmRepository() {

    }

    @Override
    public void update(Authentication authentication, CallRepository<Authentication> callRepository) {

        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(authentication);
        mRealm.commitTransaction();
        mRealm.close();

        try {
            MyApp.sSensitiveRealmConfiguration.encryptionKey(Converter.hexStringToBytes(authentication.getCredential())).build();
        } catch (Exception e) {
            e.printStackTrace();
            callRepository.onDone(new Deal<>(null, null, e));
        }

        callRepository.onDone(new Deal<>(authentication, null, null));
    }

    @Override
    public void get(Integer identifier, CallRepository<Authentication> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Authentication>> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(CallRepository<Authentication> callRepository) {

        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
        Authentication authentication1 = mRealm.where(Authentication.class).equalTo("mId", 1).findFirst();
        Deal deal;
        if (authentication1 == null) {
            deal = new Deal<>(null, null, null);
        } else {
            Authentication returnAuthentication = mRealm.copyFromRealm(authentication1);
            deal = new Deal<>(returnAuthentication, null, null);
        }
        mRealm.close();
        callRepository.onDone(deal);

    }

}