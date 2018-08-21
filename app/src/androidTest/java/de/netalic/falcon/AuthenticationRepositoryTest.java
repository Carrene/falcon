package de.netalic.falcon;

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import io.realm.Realm;

@RunWith(AndroidJUnit4.class)
public class AuthenticationRepositoryTest {

    @BeforeClass
    public static void setup() {

        MyApp.sInsensitiveRealmConfiguration = MyApp.sInsensitiveRealmConfiguration.inMemory();
    }

    @Test
    public void testAuthentication_update() {

        AuthenticationRepository.getInstance().get(deal -> Assert.assertNull(deal.getModel()));

        Authentication authentication = new Authentication();
        authentication.setAuthenticationType(1);
        authentication.setAttemptsNumber(1);
        AuthenticationRepository.getInstance().update(authentication, deal -> Assert.assertEquals(deal.getModel().getAttemptsNumber(), 1));
        AuthenticationRepository.getInstance().get(deal -> {

            Assert.assertEquals(deal.getModel().getAttemptsNumber(), 1);
            Assert.assertEquals(deal.getModel().getAuthenticationType(), 1);
            Assert.assertEquals(deal.getModel().getMaxAttemptsNumber(), 5);

        });
    }

    @Test
    public void testAuthentication_lock() {

        Authentication authentication = new Authentication();
        AuthenticationRepository.getInstance().update(authentication, deal -> Assert.assertEquals(authentication.getAttemptsNumber(), 0));
        authentication.failAttempt();
        authentication.failAttempt();
        authentication.failAttempt();
        authentication.failAttempt();
        authentication.failAttempt();
        Assert.assertTrue(authentication.isLocked());
    }

    @After
    public void deleteDatabase() {

        Realm.deleteRealm(MyApp.sInsensitiveRealmConfiguration.build());
    }
}