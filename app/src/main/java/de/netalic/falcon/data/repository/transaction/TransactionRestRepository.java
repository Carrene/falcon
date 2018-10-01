package de.netalic.falcon.data.repository.transaction;

import java.util.List;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionRestRepository implements ITransactionRepository {

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


        ApiClient.getService().startTransfer(sourceWalletId, destinationWalletId, transferAmount).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }

    @Override
    public void finalizeTransfer(int transactionId, CallRepository<Transaction> callRepository) {


        ApiClient.getService().finalizeTransfer(transactionId).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }

    @Override
    public void startCharge(int id, double amount, int verifyRateId, CallRepository<Transaction> callRepository) {
        ApiClient.getService().charge(id, amount, verifyRateId).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));

            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }

    @Override
    public void finalizeCharge(int transactionId, String braintreeNonce, CallRepository<Transaction> callRepository) {

        ApiClient.getService().finalize(transactionId, braintreeNonce).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));

            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }
}
