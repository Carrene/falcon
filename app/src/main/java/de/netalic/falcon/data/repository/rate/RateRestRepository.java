package de.netalic.falcon.data.repository.rate;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateRestRepository implements IRateRepository {

    @Override
    public void update(Rate rate, CallRepository<Rate> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(String identifier, CallRepository<Rate> callRepository) {

        ApiClient.getService().getRate(identifier).enqueue(new Callback<Rate>() {
            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {

                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<Rate> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));

            }
        });
    }

    @Override
    public void getAll(CallRepository<List<Rate>> callRepository) {

        ApiClient.getService().listRate().enqueue(new Callback<List<Rate>>() {
            @Override
            public void onResponse(Call<List<Rate>> call, Response<List<Rate>> response) {
                callRepository.onDone(new Deal<>(response.body(), response, null));
            }

            @Override
            public void onFailure(Call<List<Rate>> call, Throwable t) {

                callRepository.onDone(new Deal<>(null, null, t));
            }
        });
    }
}
