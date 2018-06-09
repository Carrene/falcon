package de.netalic.falcon.repository.user;

import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRestRepository implements IUserRepository {

    @Override
    public void bind(User user, CallRepository<User> callRepository) {

        ApiClient.getService().bind(user.getUdid(), user.getPhone(), user.calculateDeviceName(), user.getActivationCode()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }

    @Override
    public void claim(User user, CallRepository<User> callRepository) {

        ApiClient.getService().claim(user.getUdid(), user.getPhone()).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
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