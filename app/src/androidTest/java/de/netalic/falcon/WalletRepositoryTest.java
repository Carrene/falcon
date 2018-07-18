package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.repository.wallet.WalletRepository;

@RunWith(AndroidJUnit4.class)
public class WalletRepositoryTest {

    private CountDownLatch mCountDownLatch;

    @Test
    public void testGetListWallet_200response(){

        mCountDownLatch = new CountDownLatch(1);
        WalletRepository.getInstance().getAll(deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(),200);
            mCountDownLatch.countDown();
        });
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetListWallet_failure(){

        mCountDownLatch = new CountDownLatch(1);
        WalletRepository.getInstance().getAll(deal -> {

            Assert.assertNull(deal.getThrowable());
            mCountDownLatch.countDown();

        });

        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
