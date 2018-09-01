package de.netalic.falcon.data.repository.exchangeRate;

import java.util.List;

import de.netalic.falcon.data.model.Rate;

public class ExchangeRateRepository implements IExchangeRateRepository {

    private IExchangeRateRepository mExchangeRateRestRepository;
    private IExchangeRateRepository mExchangeRateRealmRepository;

    public ExchangeRateRepository(IExchangeRateRepository restRepository, IExchangeRateRepository realmRepository) {

        mExchangeRateRestRepository = restRepository;
        mExchangeRateRealmRepository = realmRepository;
    }

    private ExchangeRateRepository() {

    }

    @Override
    public void update(Rate rate, CallRepository<Rate> callRepository) {

        mExchangeRateRestRepository.update(rate, callRepository);
    }

    @Override
    public void get(String identifier, CallRepository<Rate> callRepository) {

        mExchangeRateRestRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Rate>> callRepository) {

        mExchangeRateRestRepository.getAll(callRepository);
    }
}