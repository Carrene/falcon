package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;

@RunWith(AndroidJUnit4.class)
public class ExchangeRateRepositoryTest {


    private CountDownLatch mCountDownLatch;

    @Test
    public void testGetExchangeRate_200response() {

        mCountDownLatch = new CountDownLatch(1);
        Currency currency = new UsdCurrency();
        ExchangeRateRepository.getInstance().get(currency.getCode(), deal -> {
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
        ExchangeRateRepository.getInstance().get(currency.getCode(), deal -> {

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

//    @Test
//    public void testGetExchangeRate_721response() {
//
//        mCountDownLatch = new CountDownLatch(1);
//        Currency currency = new Currency("ir");
//        ExchangeRateRepository.getInstance().get(currency.getCode(), deal -> {
//
//            Assert.assertNull(deal.getThrowable());
//            Assert.assertEquals(deal.getResponse().code(), 721);
//            Assert.assertNull(deal.getModel().getBuy(), null);
//            Assert.assertNull(deal.getModel().getSell(), null);
//
//        });
//
//        try {
//            mCountDownLatch.await();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//
//
//        }
//
//    }
//Wait for backend
//    @Test
//    public void testGetExchangeRate_failure() {
//
//        mCountDownLatch = new CountDownLatch(1);
//        ExchangeRateRepository.getInstance().get("", deal -> {
//
//            Assert.assertNotNull(deal.getThrowable());
//
//        });
//
//        try {
//            mCountDownLatch.await();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//
//
//        }
//
//    }
}
