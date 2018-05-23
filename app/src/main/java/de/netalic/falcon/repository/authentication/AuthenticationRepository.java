package de.netalic.falcon.repository.authentication;

import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.repository.IRepository;

public class AuthenticationRepository implements IRepository<Authentication> {

    private static volatile AuthenticationRepository authenticationRepository;
    private AuthenticationRealmRepository authenticationRealmRepository;

    private AuthenticationRepository() {

        authenticationRealmRepository = new AuthenticationRealmRepository();

    }

    public static AuthenticationRepository getInstance() {

        if (authenticationRepository == null) {

            synchronized (AuthenticationRepository.class) {
                if (authenticationRepository == null) {
                    authenticationRepository = new AuthenticationRepository();
                }
            }
        }
        return authenticationRepository;
    }

    @Override
    public void update(Authentication authentication) {

        authenticationRealmRepository.update(authentication);
    }

    @Override
    public Authentication get(int id) {

        return authenticationRealmRepository.get(id);
    }
}