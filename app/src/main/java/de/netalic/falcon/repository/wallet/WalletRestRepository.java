package de.netalic.falcon.repository.wallet;

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
                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<List<Wallet>> call, Throwable t) {
                callRepository.onDone(new Deal<>(null,null,t));
            }
        });
    }

    @Override
    public void getToken(int id, double amount, CallRepository<String> callRepository) {

        ApiClient.getService().getToken(id,amount).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call <String> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));
            }
        });
    }
}
