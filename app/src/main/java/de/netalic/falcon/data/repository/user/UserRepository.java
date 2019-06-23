package de.netalic.falcon.data.repository.user;

import java.util.List;

import de.netalic.falcon.data.model.User;

public class UserRepository implements IUserRepository {

    private IUserRepository mUserRestRepository;
    private IUserRepository mUserRealmRepository;

    public UserRepository(IUserRepository restRepository, IUserRepository realmRepository) {

        mUserRealmRepository = realmRepository;
        mUserRestRepository = restRepository;
    }

    private UserRepository() {

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
    public void insert(User user, CallRepository<User> callRepository) {

        mUserRealmRepository.insert(user,callRepository);
    }

    @Override
    public void updateCurrency(int userId,String baseCurrencyCode, CallRepository<User> callRepository) {
        mUserRestRepository.updateCurrency(userId,baseCurrencyCode,callRepository);
    }


    @Override
    public void update(User user, CallRepository<User> callRepository) {

        mUserRealmRepository.update(user,callRepository);

    }

    @Override
    public void get(Integer identifier, CallRepository<User> callRepository) {

        mUserRealmRepository.get(identifier, callRepository);

    }

    @Override
    public void getAll(CallRepository<List<User>> callRepository) {

        mUserRestRepository.getAll(callRepository);

    }
}