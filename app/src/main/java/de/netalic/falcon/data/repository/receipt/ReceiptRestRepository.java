package de.netalic.falcon.data.repository.receipt;

import java.util.List;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import de.netalic.falcon.data.repository.base.IRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptRestRepository implements IReceiptRepository {
    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

    }


    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {

        ApiClient.getService().transfer(sourceAddress,walletId,amount).enqueue(new Callback<Receipt>() {
            @Override
            public void onResponse(Call<Receipt> call, Response<Receipt> response) {

                callRepository.onDone(new Deal<Receipt>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<Receipt> call, Throwable t) {
                callRepository.onDone(new Deal<Receipt>(null,null,t));

            }
        });

    }
}
