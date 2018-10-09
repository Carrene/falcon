package de.netalic.falcon.data.repository.currency;

import java.util.List;

import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRestRepository implements ICurrencyRepository {



    @Override
    public void update(Currency currency, CallRepository<Currency> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(Integer identifier, CallRepository<Currency> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Currency>> callRepository) {


        ApiClient.getService().currencyList().enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {

                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));
            }
        });
    }
}
