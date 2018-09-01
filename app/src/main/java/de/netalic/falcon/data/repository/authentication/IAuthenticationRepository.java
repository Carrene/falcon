package de.netalic.falcon.data.repository.authentication;

import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.data.model.Authentication;

public interface IAuthenticationRepository extends IRepository<Authentication, Integer> {

    void get(CallRepository<Authentication> callRepository);
}
