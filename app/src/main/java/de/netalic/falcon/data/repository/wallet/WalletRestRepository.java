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

    @Override
    public void addWallet(String walletName, String walletCurrencyCode, CallRepository<Wallet> walletCallRepository) {

        ApiClient.getService().addWallet(walletName,walletCurrencyCode).enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {

                walletCallRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {

                walletCallRepository.onDone(new Deal<>(null,null,t));
            }
        });

    }
}
