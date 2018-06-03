package de.netalic.falcon.repository.user;

import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;

public class UserRealmRepository implements IUserRepository {

    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {

        return null;
    }


    @Override
    public Deferred<Deal<User, UserSource>, Throwable, Object> bind(User user) {

        return new DeferredObject<Deal<User, UserSource>, Throwable, Object>().reject(new UnsupportedOperationException());

    }

    @Override
    public Deferred<Deal<User, UserSource>, Throwable, Object> claim(User user) {

        return null;
    }
}
