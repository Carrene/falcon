package de.netalic.falcon.repository;

import android.provider.Settings;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DeviceUtil.class, Settings.Secure.class,MyApp.class})
@PowerMockIgnore("javax.net.ssl.*")

public class UserRepositoryTest {

    private UserRepository mUserRepository;
    private static MockWebServer sMockWebServer;
    private User mUser;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        PowerMockito.mockStatic(Settings.Secure.class);
        PowerMockito.mockStatic(MyApp.class);

        MyApp myApp = mock(MyApp.class);
        Mockito.when(MyApp.getInstance()).thenReturn(myApp);
        Mockito.when(DeviceUtil.getSecureId(myApp)).thenReturn("123456");

        mUserRepository = UserRepository.getInstance();
        sMockWebServer = new MockWebServer();

        try {
            sMockWebServer.start(0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to start server");
        }
    }

    @Test
    public void claimUser() {

        System.out.println(ApiClient.getApiAddress());
        CountDownLatch countDownLatch = new CountDownLatch(1);
        String json = "{\"phone\":\"989122451075\",\"udid\":\"7C4A8D09CA3762AF61E59520943DC26494F8941B\"}";
        sMockWebServer.enqueue(new MockResponse().setBody(json));
        mUser = new User("+981234567890");

        mUserRepository.claim(mUser).promise().then(result -> {
            Assert.assertNotNull(result.getModel());
            Assert.assertEquals(result.getModel().getPhone(), "+981234567890");
            Assert.assertEquals(result.getModel().getUdid(), "7C4A8D09CA3762AF61E59520943DC26494F8941B");
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
            if (sMockWebServer != null)
                sMockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Unable to stop server");
        }
    }
}