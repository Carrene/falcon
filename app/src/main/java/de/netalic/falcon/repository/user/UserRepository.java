package de.netalic.falcon.repository.user;

import org.jdeferred.Deferred;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;

public class UserRepository implements IUserRepository {

    private static volatile UserRepository sUserRepository;
    private UserRestRepository mUserRestRepository;
    private UserRealmRepository mUserRealmRepository;

    private UserRepository() {

        mUserRestRepository = new UserRestRepository();
        mUserRealmRepository = new UserRealmRepository();

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
    public Deferred<Deal<User, UserSource>, Throwable, Object> login(User user) {

        return mUserRealmRepository.login(user);
    }
}