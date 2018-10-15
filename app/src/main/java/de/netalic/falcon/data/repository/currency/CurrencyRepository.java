package de.netalic.falcon.data.repository.currency;

import java.util.List;

import de.netalic.falcon.data.model.Currency;

public class CurrencyRepository implements ICurrencyRepository {

    private CurrencyRestRepository mCurrencyRestRepository;


    public CurrencyRepository(CurrencyRestRepository currencyRestRepository) {
        mCurrencyRestRepository = currencyRestRepository;
    }

    public CurrencyRepository() {

    }

    public CurrencyRepository(CurrencyRestRepository currencyRestRepository, Object o) {

        mCurrencyRestRepository=currencyRestRepository;
    }


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

        mCurrencyRestRepository.getAll(callRepository);
    }
}
