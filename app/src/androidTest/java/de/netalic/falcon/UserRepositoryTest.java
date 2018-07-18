package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryTest {

    private CountDownLatch mCountDownLatch;

    @Test
    public void testClaimUser_200Response(){

        mCountDownLatch=new CountDownLatch(1);

        User user=new User("9367150742");
        UserRepository.getInstance().claim(user,deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(),200);
            mCountDownLatch.countDown();
        });
    }

    @Test
    public void testClaimUser_710Response(){

        mCountDownLatch=new CountDownLatch(2);
        User user=new User("abc");
        User user1=new User("");
        UserRepository.getInstance().claim(user,deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(),710);
            mCountDownLatch.countDown();
        });
        UserRepository.getInstance().claim(user1,deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(),710);
            mCountDownLatch.countDown();
        });
    }

    @Test
    public void testBindUser_200Response(){

        mCountDownLatch=new CountDownLatch(1);
        User user=new User();
        user.getUdid();
        user.getDeviceName();




    }
}
