package de.netalic.falcon.data.repository.authentication;

import java.util.List;

import de.netalic.falcon.data.model.Authentication;

public class AuthenticationRepository implements IAuthenticationRepository {

    private IAuthenticationRepository mAuthenticationRealmRepository;
    private IAuthenticationRepository mAuthenticationRestRepository;

    public AuthenticationRepository(IAuthenticationRepository restRepository, IAuthenticationRepository realmRepository) {

        mAuthenticationRestRepository = restRepository;
        mAuthenticationRealmRepository = realmRepository;
    }

    private AuthenticationRepository() {

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