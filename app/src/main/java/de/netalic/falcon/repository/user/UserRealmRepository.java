package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;

public class UserRealmRepository implements IUserRepository {

    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {

        return null;
    }

    @Override
    public void login(User user) {

        throw new UnsupportedOperationException();
    }
}
