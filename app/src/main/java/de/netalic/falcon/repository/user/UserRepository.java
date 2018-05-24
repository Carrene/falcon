package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;

public class UserRepository implements IUserRepository {

    private static volatile UserRepository sUserRepository;
    private UserRestRepository mUserRestRepository;

    private UserRepository() {

        mUserRestRepository = new UserRestRepository();

    }

    public static UserRepository getInstance() {

        if (sUserRepository == null) {

            synchronized (UserRepository.class) {
                if (sUserRepository == null) {
                    sUserRepository = new UserRepository();
                }
            }
        }
        return sUserRepository;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {

        return null;
    }

    @Override
    public void login(User user) {
        //TODO: Add request for login
    }
}