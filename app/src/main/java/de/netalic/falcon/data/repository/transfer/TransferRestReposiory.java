package de.netalic.falcon.data.repository.transfer;

import java.util.List;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferRestReposiory implements ITransferRepository {

    @Override
    public void update(Transaction transaction, CallRepository<Transaction> callRepository) {

        throw new UnsupportedOperationException();

    }

    @Override
    public void get(Integer identifier, CallRepository<Transaction> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Transaction>> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void startTransfer(int sourceWalletId, String destinationWalletId, float transferAmount, CallRepository<Transaction> callRepository) {


        ApiClient.getService().StartTransfer(sourceWalletId,destinationWalletId,transferAmount).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));
            }
        });
    }
}
