package de.netalic.falcon.repository.exchangeRate;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.repository.user.UserRepository;

public class ExchangeRateRepository implements IExchangeRate {

    private static volatile ExchangeRateRepository sExchangeRateRepository;
    private ExchangeRateRestRepository mExchangeRateRestRepository;

    private ExchangeRateRepository() {

        mExchangeRateRestRepository = new ExchangeRateRestRepository();

    }

    public ExchangeRateRepository(ExchangeRateRestRepository mExchangeRateRestRepository) {

        this.mExchangeRateRestRepository = mExchangeRateRestRepository;
    }


    public static ExchangeRateRepository getInstance() {

        if (sExchangeRateRepository == null) {

            synchronized (UserRepository.class) {
                if (sExchangeRateRepository == null) {
                    sExchangeRateRepository = new ExchangeRateRepository();
                }
            }
        }
        return sExchangeRateRepository;
    }

    @Override
    public void exchangeRate(Currency currency, CallRepository<ExchangeRate> callRepository) {

        mExchangeRateRestRepository.exchangeRate(currency, callRepository);
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
