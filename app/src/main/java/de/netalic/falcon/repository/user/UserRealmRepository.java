package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;

public class UserRealmRepository implements IUserRepository {
    @Override
    public void bind(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void claim(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void setEmail(User user, CallRepository<User> callRepository) {
        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void update(User user, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }

    @Override
    public void get(Integer identifier, CallRepository<User> callRepository) {

        callRepository.onDone(new Deal<>(null, null, new UnsupportedOperationException()));

    }
}
