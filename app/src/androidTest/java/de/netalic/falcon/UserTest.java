package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;

@RunWith(AndroidJUnit4.class)
public class UserTest {


    @Test
    public void testClaim() {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        User user = new User();
        user.setPhone("+989377426996");
        UserRepository.getInstance().claim(user).promise().done(result -> {

            Assert.assertEquals(result.getModel().getPhone(), "+989377426996");
            countDownLatch.countDown();

        }).fail(result -> {
            Assert.fail(result.toString());
            Assert.fail();
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBind() {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        User user = new User();
        user.setPhone("+989377426996");
        UserRepository.getInstance().bind(user).promise().done(result -> {

            Assert.assertEquals(user.getPhone(), "+989377426996");
            countDownLatch.countDown();


        }).fail(result -> {
            Assert.fail(result.toString());
            Assert.fail();
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
