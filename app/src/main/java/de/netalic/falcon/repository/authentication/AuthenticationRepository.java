package de.netalic.falcon.repository.authentication;

import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.repository.IRepository;

public class AuthenticationRepository implements IAuthenticationRepository {

    private static volatile AuthenticationRepository sAuthenticationRepository;
    private AuthenticationRealmRepository mAuthenticationRealmRepository;

    private AuthenticationRepository() {

        mAuthenticationRealmRepository = new AuthenticationRealmRepository();

    }

    public static AuthenticationRepository getInstance() {

        if (sAuthenticationRepository == null) {

            synchronized (AuthenticationRepository.class) {
                if (sAuthenticationRepository == null) {
                    sAuthenticationRepository = new AuthenticationRepository();
                }
            }
        }
        return sAuthenticationRepository;
    }

    @Override
    public void update(Authentication authentication) {

        mAuthenticationRealmRepository.update(authentication);
    }

    @Override
    public Authentication get(int id) {

        return mAuthenticationRealmRepository.get(id);
    }
}