package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.data.model.Currency;

@RunWith(AndroidJUnit4.class)
public class RateRepositoryAndroidTest {


    private CountDownLatch mCountDownLatch;

    @Test
    public void testGetExchangeRate_200response() {

        mCountDownLatch = new CountDownLatch(1);
        Currency currency = new Currency("usd");
        RepositoryLocator.getInstance().getRepository(ExchangeRateRepository.class).get(currency.getCode(), deal -> {
            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 200);
            mCountDownLatch.countDown();
        });

        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetExchangeRate_709response() {

        mCountDownLatch = new CountDownLatch(1);
        Currency currency = new Currency("milad");
        RepositoryLocator.getInstance().getRepository(ExchangeRateRepository.class).get(currency.getCode(), deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 709);
            mCountDownLatch.countDown();
        });

        try {
            mCountDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();


        }

    }

    @Test
    public void testGetExchangeRate_721response() {

        mCountDownLatch = new CountDownLatch(1);
        Currency currency = new Currency("yen");
        RepositoryLocator.getInstance().getRepository(ExchangeRateRepository.class).get(currency.getCode(), deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 721);
            Assert.assertNull(String.valueOf(deal.getResponse().body()), null);
            mCountDownLatch.countDown();
        });

        try {
            mCountDownLatch.await();

        } catch (InterruptedException e) {
            e.printStackTrace();


        }

    }

}
