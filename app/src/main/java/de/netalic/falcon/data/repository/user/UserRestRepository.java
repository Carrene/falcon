package de.netalic.falcon.data.repository.user;

import java.util.List;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
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
    public void setEmail(User user, CallRepository<User> callRepository) {

        ApiClient.getService().setEmail(user.getEmail()).enqueue(new Callback<User>() {

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
    public void insert(User user, CallRepository<User> callRepository) {

    }

    @Override
    public void updateCurrency(int userId, String baseCurrencyCode, CallRepository<User> callRepository) {

        ApiClient.getService().changeBaseCurrency(userId,baseCurrencyCode).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));
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

    @Override
    public void getAll(CallRepository<List<User>> callRepository) {

        throw new UnsupportedOperationException();

    }
}