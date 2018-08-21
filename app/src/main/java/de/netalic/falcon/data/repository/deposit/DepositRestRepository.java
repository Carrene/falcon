package de.netalic.falcon.data.repository.deposit;

import java.util.List;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.Deal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositRestRepository implements IDepositRepository {



    @Override
    public void update(Deposit deposit, CallRepository<Deposit> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void get(Integer identifier, CallRepository<Deposit> callRepository) {

        throw new UnsupportedOperationException();
    }

    @Override
    public void getAll(CallRepository<List<Deposit>> callRepository) {

   ApiClient.getService().depositList().enqueue(new Callback<List<Deposit>>() {
       @Override
       public void onResponse(Call<List<Deposit>> call, Response<List<Deposit>> response) {

           callRepository.onDone(new Deal<>(response.body(),response,null));
       }

       @Override
       public void onFailure(Call<List<Deposit>> call, Throwable t) {

           callRepository.onDone(new Deal<>(null,null,t));

       }
   });

    }
}
