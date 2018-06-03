package de.netalic.falcon.repository;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.network.ApiInterface;
import de.netalic.falcon.repository.user.UserRepository;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.Mockito.when;

public class UserRepositoryTest {

    private UserRepository userRepository;
    private static MockWebServer mockWebServer;
    private User user;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        userRepository = UserRepository.getInstance();
        mockWebServer = new MockWebServer();
        //TODO: mock api client address to localhost:8585
        try {
            mockWebServer.start(8585);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void claimUser() {

        System.out.println(ApiClient.getApiAddress());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String json = "{\"phone\":\"989122451075\",\"udid\":\"2b6f0cc904d137be2e1730235f5664094b831186\"}";
        mockWebServer.enqueue(new MockResponse().setBody(json));
        user = new User("+981234567890");

        userRepository.claim(user).promise().then(result -> {
            Assert.assertNotNull(result.getModel());
            Assert.assertEquals(result.getModel().getPhone(), "+981234567890");
            Assert.assertEquals(result.getModel().getUdid(), "2b6f0cc904d137be2e1730235f5664094b831186");
            countDownLatch.countDown();

        }).fail(result -> {
            Assert.fail();
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void shutdown() {

        try {
            if (mockWebServer != null)
                mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}