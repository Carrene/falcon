package de.netalic.falcon.data.repository.authentication;

import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IAuthenticationRepository extends IRepository<Authentication, Integer> {

    void get(CallRepository<Authentication> callRepository);
    void checkCredentialValue(String credentialValue,CallRepository<Authentication>callRepository);
}
