package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;

@RunWith(AndroidJUnit4.class)
public class ExchangeRateRepositoryTest {

    @Test
    public void testGetExchangeRate_200Response() {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        Currency currency = new UsdCurrency();
        ExchangeRateRepository.getInstance().get(currency.getCode(), deal -> {
            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 200);
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
