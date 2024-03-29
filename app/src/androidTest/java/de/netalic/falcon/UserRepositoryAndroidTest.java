package de.netalic.falcon;

import android.util.Base64;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.remote.ApiClient;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.util.FileUtil;
import nuesoft.helpdroid.crypto.CryptoUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(AndroidJUnit4.class)
public class UserRepositoryAndroidTest {

    private static MockWebServer sMockWebServer;

    @Before
    public void setupServer() {

        sMockWebServer = new MockWebServer();

        try {
            sMockWebServer.start(8586);
            ApiClient.setService(ApiClient.getMockUpClient("http://" + sMockWebServer.getHostName() + ":" + sMockWebServer.getPort()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void shutdownServer() {

        try {
            sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClaimUser_200Response() {

        UserRepository userRepository = RepositoryLocator.getInstance().getRepository(UserRepository.class);

        String json = null;
        try {
            json = FileUtil.readTextFromStream(getClass().getResourceAsStream("claim.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        User user = new User("+981234567890");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        userRepository.claim(user, deal -> {
            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 200);
            Assert.assertEquals(deal.getModel().getPhone(), "989122451075");
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBindUser_200Response() {

        User user = new User("Fake", "Fake", "Fake");
        String json = null;
        try {
            json = FileUtil.readTextFromStream(getClass().getResourceAsStream("bind.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        CountDownLatch countDownLatch = new CountDownLatch(1);
        RepositoryLocator.getInstance().getRepository(UserRepository.class).bind(user, deal -> {
            Assert.assertNotNull(deal.getModel());
            try {
                byte[] cipherText = CryptoUtil.encryptAesCbcPkcs5Padding(Base64.decode(deal.getModel().getSecret(), Base64.DEFAULT), CryptoUtil.getSecureRandom(16), "This is test".getBytes("UTF-8"));
                Assert.assertNotNull(cipherText);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}