package de.netalic.falcon.data.repository.wallet;

import java.util.List;

import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.model.Wallet;
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
    public void charge(int id, double amount, CallRepository<Deposit> callRepository) {

        ApiClient.getService().charge(id, amount).enqueue(new Callback<Deposit>() {
            @Override
            public void onResponse(Call<Deposit> call, Response<Deposit> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));

            }

            @Override
            public void onFailure(Call<Deposit> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }

    @Override
    public void finalize(int walletId, int depositId,String braintreeNonce, CallRepository<Deposit> callRepository) {
        ApiClient.getService().finalize(walletId,depositId,braintreeNonce).enqueue(new Callback<Deposit>() {
            @Override
            public void onResponse(Call<Deposit> call, Response<Deposit> response) {

                callRepository.onDone(new Deal<>(response.body(),response,null));

            }

            @Override
            public void onFailure(Call<Deposit> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));

            }
        });
    }


}
