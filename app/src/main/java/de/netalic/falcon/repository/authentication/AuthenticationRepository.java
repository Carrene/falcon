package de.netalic.falcon.repository.authentication;

import de.netalic.falcon.MyApp;
import io.realm.Realm;

public class AuthenticationRepository {

    private static volatile AuthenticationRepository INSTANCE;
    private Realm mRealm;

    private AuthenticationRepository() {

        mRealm = Realm.getInstance(MyApp.insensitiveRealmConfiguration.build());
    }

    public static AuthenticationRepository getInstance() {

        if (INSTANCE == null) {

            synchronized (AuthenticationRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthenticationRepository();
                }
            }
        }
        return INSTANCE;
    }
}