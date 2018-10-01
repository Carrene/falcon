package de.netalic.falcon.data.repository.wallet;

import java.util.List;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
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

}
