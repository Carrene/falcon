package de.netalic.falcon.repository.wallet;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletRestRepository implements IWalletRepository {

    @Override
    public void update(Wallet wallet, CallRepository<Wallet> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(Integer identifier, CallRepository<Wallet> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Wallet>> callRepository) {

        ApiClient.getService().walletList().enqueue(new Callback<List<Wallet>>() {
            @Override
            public void onResponse(Call<List<Wallet>> call, Response<List<Wallet>> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<List<Wallet>> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }

    @Override
    public void charge(int id, double amount, CallRepository<JsonObject> callRepository) {

        ApiClient.getService().charge(id, amount).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }

    @Override
    public void finalize(double amount, String braintreeNonce, String chargeDataToken, CallRepository<JsonObject> callRepository) {

        ApiClient.getService().finalize(amount, braintreeNonce, chargeDataToken).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }

}
