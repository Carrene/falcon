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
    public void testLoginSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        User user = new User();
        user.setPhone("+989377426996");
        String udid = user.calculateUdid();
        UserRepository.getInstance().login(user).promise().done(result -> {

            countDownLatch.countDown();
            Assert.assertEquals(user.getPhone(), "+989377426996");
            Assert.assertEquals(user.getUdid(), udid);

        }).fail(result -> {
            Assert.fail(result.toString());
            countDownLatch.countDown();
            Assert.fail();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
