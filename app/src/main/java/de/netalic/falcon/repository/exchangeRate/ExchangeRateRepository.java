package de.netalic.falcon.repository.exchangeRate;

import de.netalic.falcon.model.ExchangeRate;

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
    public void exchangeRate(ExchangeRate exchangeRate, CallRepository<ExchangeRate> callRepository) {

        mExchangeRateRestRepository.exchangeRate(exchangeRate, callRepository);
    }

    @Override
    public void update(ExchangeRate exchangeRate, CallRepository<ExchangeRate> callRepository) {

        mExchangeRateRestRepository.update(exchangeRate,callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<ExchangeRate> callRepository) {

        mExchangeRateRestRepository.get(identifier,callRepository);

    }
}
