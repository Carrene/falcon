package de.netalic.falcon.data.repository.user;

import de.netalic.falcon.model.User;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IUserRepository extends IRepository<User, Integer> {

    void bind(User user, CallRepository<User> callRepository);

    void claim(User user, CallRepository<User> callRepository);

    void setEmail(User user, CallRepository<User> callRepository);

}
