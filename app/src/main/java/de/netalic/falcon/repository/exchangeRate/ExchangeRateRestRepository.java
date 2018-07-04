package de.netalic.falcon.repository.exchangeRate;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExchangeRateRestRepository implements IExchangeRate {
    @Override
    public void exchangeRate(Currency currency, CallRepository<ExchangeRate> callRepository) {

        ApiClient.getService().exchangeRate(currency.getName()).enqueue(new Callback<ExchangeRate>() {
            @Override
            public void onResponse(Call<ExchangeRate> call, Response<ExchangeRate> response) {

                callRepository.onDone(new Deal<>(response.body(),response,null));
            }

            @Override
            public void onFailure(Call<ExchangeRate> call, Throwable t) {

                callRepository.onDone(new Deal<>(null,null,t));

            }
        });
    }

    @Override
    public void update(ExchangeRate exchangeRate, CallRepository<ExchangeRate> callRepository) {

    }

    @Override
    public void get(Integer identifier, CallRepository<ExchangeRate> callRepository) {

    }
}
