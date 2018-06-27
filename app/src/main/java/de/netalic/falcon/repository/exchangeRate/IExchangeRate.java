package de.netalic.falcon.repository.exchangeRate;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.repository.IRepository;

public interface IExchangeRate extends IRepository<ExchangeRate,Integer> {

    void exchangeRate(Currency currency, CallRepository<ExchangeRate>callRepository);
}
