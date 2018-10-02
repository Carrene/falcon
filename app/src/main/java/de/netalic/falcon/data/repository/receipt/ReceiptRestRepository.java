package de.netalic.falcon.data.repository.receipt;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptRestRepository implements IReceiptRepository {
    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

        ApiClient.getService().getReceipt(identifier).enqueue(new Callback<Receipt>() {
            @Override
            public void onResponse(Call<Receipt> call, Response<Receipt> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<Receipt> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

        ApiClient.getService().receiptList().enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }


    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {


    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository, Map<String, String> query, int take, int skip) {

        ApiClient.getService().receiptList(query, take, skip).enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }
}
