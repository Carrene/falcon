package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.repository.authentication.AuthenticationRepository;

@RunWith(AndroidJUnit4.class)
public class AuthenticationTest {

    @BeforeClass
    public static void setup() {

        MyApp.insensitiveRealmConfiguration = MyApp.insensitiveRealmConfiguration.inMemory();
    }

    @Test
    public void testAuthentication() {

        Authentication authentication = AuthenticationRepository.getInstance().get(1);
        Assert.assertNull(authentication);
        authentication = new Authentication();
        authentication.setAuthenticationType(1);
        authentication.setAttemptsNumber(1);
        AuthenticationRepository.getInstance().update(authentication);
        authentication = AuthenticationRepository.getInstance().get(1);
        Assert.assertEquals(authentication.getAttemptsNumber(), 1);
        Assert.assertEquals(authentication.getAuthenticationType(), 1);
        Assert.assertEquals(authentication.getMaxAttemptsNumber(), 5);
        authentication.failAttempt();
        AuthenticationRepository.getInstance().update(authentication);
        Assert.assertEquals(authentication.getAttemptsNumber(), 2);
        authentication.successAttempt();
        Assert.assertEquals(authentication.getAttemptsNumber(), 0);
    }
}
