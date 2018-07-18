package de.netalic.falcon.repository.exchangeRate;

import java.util.List;

import de.netalic.falcon.model.Rate;

public class ExchangeRateRepository implements IExchangeRate {

    private static volatile ExchangeRateRepository sExchangeRateRepository;
    private ExchangeRateRestRepository mExchangeRateRestRepository;

    private ExchangeRateRepository() {

        mExchangeRateRestRepository = new ExchangeRateRestRepository();
    }

    public static ExchangeRateRepository getInstance() {

        if (sExchangeRateRepository == null) {

            synchronized (ExchangeRateRepository.class) {
                if (sExchangeRateRepository == null) {
                    sExchangeRateRepository = new ExchangeRateRepository();
                }
            }
        }
        return sExchangeRateRepository;
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