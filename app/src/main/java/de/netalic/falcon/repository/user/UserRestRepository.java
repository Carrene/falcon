package de.netalic.falcon.repository.user;

import org.jdeferred.Deferred;
import org.jdeferred.impl.DeferredObject;

import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRestRepository implements IUserRepository {


    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {

        return null;
    }

    @Override
    public Deferred<Deal<User, UserSource>, Throwable, Object> bind(User user) {

        Deferred<Deal<User, UserSource>, Throwable, Object> deferredObject = new DeferredObject<>();
        ApiClient.getService().bind(user.getUdid(), user.getPhone(), user.calculateDeviceName(), user.getActivationCode()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                deferredObject.resolve(new Deal<>(user, response, UserSource.API));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                deferredObject.reject(t);
            }
        });

        return deferredObject;
    }

    @Override
    public Deferred<Deal<User, UserSource>, Throwable, Object> claim(User user) {

        Deferred<Deal<User, UserSource>, Throwable, Object> deferredObject = new DeferredObject<>();
        ApiClient.getService().claim(user.getUdid(), user.getPhone()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                deferredObject.resolve(new Deal<>(user, response, UserSource.API));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                deferredObject.reject(t);
            }
        });

        return deferredObject;
    }
}