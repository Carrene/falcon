package de.netalic.falcon.data.repository.authentication;

import java.util.List;

import de.netalic.falcon.model.Authentication;

public class  AuthenticationRepository implements IAuthenticationRepository {

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
    public void get(CallRepository<Authentication> callRepository) {

        mAuthenticationRealmRepository.get(callRepository);
    }

    @Override
    public void update(Authentication authentication, CallRepository<Authentication> callRepository) {

        mAuthenticationRealmRepository.update(authentication, callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<Authentication> callRepository) {

        mAuthenticationRealmRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Authentication>> callRepository) {

        mAuthenticationRealmRepository.getAll(callRepository);
    }
}