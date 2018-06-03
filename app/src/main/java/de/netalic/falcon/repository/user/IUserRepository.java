package de.netalic.falcon.repository.user;

import org.jdeferred.Deferred;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;

public interface IUserRepository extends IRepository<User> {

    Deferred<Deal<User, UserSource>, Throwable, Object> bind(User user);

    Deferred<Deal<User, UserSource>, Throwable, Object> claim(User user);
}
