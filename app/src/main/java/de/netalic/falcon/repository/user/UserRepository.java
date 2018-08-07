package de.netalic.falcon.repository.user;

import java.util.List;

import de.netalic.falcon.model.User;

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
    public void bind(User user, CallRepository<User> callRepository) {

        mUserRestRepository.bind(user, deal -> {
            if (deal.getThrowable() == null && deal.getResponse().code() == 200) {
                mUserRealmRepository.update(deal.getModel(), deal1 -> {
                });

            }
            callRepository.onDone(deal);

        });
    }

    @Override
    public void claim(User user, CallRepository<User> callRepository) {

        mUserRestRepository.claim(user, callRepository);

    }

    @Override
    public void setEmail(User user, CallRepository<User> callRepository) {

        mUserRestRepository.setEmail(user, callRepository);
    }


    @Override
    public void update(User user, CallRepository<User> callRepository) {

        mUserRestRepository.update(user, callRepository);

    }

    @Override
    public void get(Integer identifier, CallRepository<User> callRepository) {

        mUserRestRepository.get(identifier, callRepository);

    }

    @Override
    public void getAll(CallRepository<List<User>> callRepository) {

        mUserRestRepository.getAll(callRepository);

    }
}