package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;

public class UserRestRepository implements IUserRepository {


    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {

        return null;
    }

    @Override
    public void login(User user) {
        //TODO: Add request with retrofit
    }
}