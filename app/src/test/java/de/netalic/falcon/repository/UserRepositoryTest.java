package de.netalic.falcon.repository;

import android.provider.Settings;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.user.UserRepository;
import nuesoft.helpdroid.device.DeviceUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DeviceUtil.class, Settings.Secure.class, MyApp.class})
@PowerMockIgnore("javax.net.ssl.*")

public class UserRepositoryTest {

    private UserRepository mUserRepository;
    private static MockWebServer sMockWebServer;
    private User mUser;
    @Mock
    private MyApp mMyApp;

    @BeforeClass
    public static void setUp() {

        MockitoAnnotations.initMocks(UserRepository.class);
    }

    @Before
    public void setupServer() {

        sMockWebServer = new MockWebServer();
        try {
            sMockWebServer.start(8586);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to start server");
        }
        ApiClient.sTestUrl = sMockWebServer.url("/").toString();
    }

    @Test
    public void testClaimUser_200Response() {

        PowerMockito.mockStatic(Settings.Secure.class);
        PowerMockito.mockStatic(MyApp.class);
        PowerMockito.mockStatic(DeviceUtil.class);
        Mockito.when(MyApp.getInstance()).thenReturn(mMyApp);
        Mockito.when(DeviceUtil.getSecureId(mMyApp)).thenReturn("123456");
        Mockito.when(DeviceUtil.getDeviceName()).thenReturn("SM-12345678");

        mUserRepository = UserRepository.getInstance();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        String json = "{\"phone\":\"989122451075\",\"udid\":\"7C4A8D09CA3762AF61E59520943DC26494F8941B\"}";
        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        mUser = new User("+981234567890");
        mUserRepository.claim(mUser, deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 200);
            Assert.assertEquals(deal.getModel().getPhone(), "989122451075");
            Assert.assertEquals(deal.getModel().getUdid(), "7C4A8D09CA3762AF61E59520943DC26494F8941B");
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

        PowerMockito.mockStatic(Settings.Secure.class);
        PowerMockito.mockStatic(MyApp.class);
        PowerMockito.mockStatic(DeviceUtil.class);
        Mockito.when(MyApp.getInstance()).thenReturn(mMyApp);
        Mockito.when(DeviceUtil.getSecureId(mMyApp)).thenReturn("123456");
        Mockito.when(DeviceUtil.getDeviceName()).thenReturn("SM-12345678");

        mUserRepository = UserRepository.getInstance();

        String json = "{\"phone\":989122451075,\"udid\":\"2b6f0cc904d137be2e1730235f5664094b831186\",\"deviceName\":\"SM-12345678\",\"createdAt\":\"2018-05-29T17:52:48.414538Z\",\"type\":\"client\",\"hmacSecret\":\"4FSEfaboM2ExMvY8zHuj5wxabsdhX0xenqvpWAeKIQs=\",\"secret\":\"hKwi8TZzULSunXWzVoAFl6AxG9qVgopToGONaGtM31g=\",\"id\":1,\"isActive\":false,\"token\":\"eyJhbGciOiJIUzI1NiIsImlhdCI6MTUyNzYwMDE3MCwiZXhwIjoxNTI3Njg2NTcwfQ.eyJpZCI6MSwicGhvbmUiOjk4OTEyMjQ1MTA3NSwicm9sZXMiOlsiY2xpZW50Il0sInNlc3Npb25JZCI6ImI4Y2NlY2ZlLTc2NDctNDlhYy1iYjU1LWQ2MDc5YzhjYThiZCJ9.Rm7pays5ZfryMSt0-fq4mAIvf2iAVcs2WC3n4TEUkDY\",\"isNewClient\":false}";

        CountDownLatch countDownLatch = new CountDownLatch(1);

        sMockWebServer.enqueue(new MockResponse().setBody(json).setResponseCode(200));

        mUser = new User("+981234567890");
        mUser.setActivationCode("123456");
        mUserRepository.bind(mUser, deal -> {

            Assert.assertNull(deal.getThrowable());
            Assert.assertEquals(deal.getResponse().code(), 200);
            Assert.assertEquals(deal.getModel().getPhone(), "989122451075");
            Assert.assertEquals(deal.getModel().getUdid(), "7C4A8D09CA3762AF61E59520943DC26494F8941B");
            Assert.assertEquals(deal.getModel().getDeviceName(), "SM-12345678");

            countDownLatch.countDown();

        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClaimUser_failure() {

        PowerMockito.mockStatic(Settings.Secure.class);
        PowerMockito.mockStatic(MyApp.class);
        PowerMockito.mockStatic(DeviceUtil.class);
        Mockito.when(MyApp.getInstance()).thenReturn(mMyApp);
        Mockito.when(DeviceUtil.getSecureId(mMyApp)).thenReturn("123456");
        Mockito.when(DeviceUtil.getDeviceName()).thenReturn("SM-12345678");

        mUserRepository = UserRepository.getInstance();
        try {
            sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);

        sMockWebServer.enqueue(new MockResponse());

        mUser = new User("+981234567890");
        mUserRepository.claim(mUser, deal -> {
            Assert.assertNull(deal.getModel());
            Assert.assertNull(deal.getResponse());
            Assert.assertNotNull(deal.getThrowable());
            countDownLatch.countDown();

        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBindUser_failure() {

        PowerMockito.mockStatic(Settings.Secure.class);
        PowerMockito.mockStatic(MyApp.class);
        PowerMockito.mockStatic(DeviceUtil.class);
        Mockito.when(MyApp.getInstance()).thenReturn(mMyApp);
        Mockito.when(DeviceUtil.getSecureId(mMyApp)).thenReturn("123456");
        Mockito.when(DeviceUtil.getDeviceName()).thenReturn("SM-12345678");

        mUserRepository = UserRepository.getInstance();
        try {
            sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);

        sMockWebServer.enqueue(new MockResponse());

        mUser = new User("+981234567890");
        mUserRepository.bind(mUser, deal -> {
            Assert.assertNull(deal.getModel());
            Assert.assertNull(deal.getResponse());
            Assert.assertNotNull(deal.getThrowable());
            countDownLatch.countDown();

        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUserUpdate_exception() {

        mUserRepository = UserRepository.getInstance();
        mUserRepository.update(new User(), deal -> {
            Assert.assertEquals(deal.getThrowable().getClass(), UnsupportedOperationException.class);

        });
    }

    @Test
    public void testUserGet_exception() {

        mUserRepository = UserRepository.getInstance();
        mUserRepository.get(1, deal -> {
            Assert.assertEquals(deal.getThrowable().getClass(), UnsupportedOperationException.class);
        });
    }

    @After
    public void shutdown() {

        try {
            if (sMockWebServer != null)
                sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to stop server");
        }
    }
}