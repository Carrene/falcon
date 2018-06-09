package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.IRepository;

public interface IUserRepository extends IRepository<User, Integer> {

    void bind(User user, CallRepository<User> callRepository);

    void claim(User user, CallRepository<User> callRepository);


}
