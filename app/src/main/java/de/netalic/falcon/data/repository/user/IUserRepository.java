package de.netalic.falcon.data.repository.user;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IUserRepository extends IRepository<User, Integer> {

    void bind(User user, CallRepository<User> callRepository);

    void claim(User user, CallRepository<User> callRepository);

    void setEmail(User user, CallRepository<User> callRepository);

    void insert(User user,CallRepository<User> callRepository);

    void updateCurrency(int userId,String baseCurrencyCode, CallRepository<User> callRepository);

}
