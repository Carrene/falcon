package de.netalic.falcon;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.SecretKey;

import de.netalic.falcon.security.KeyManager;

public class KeyDerivationTest {

    private static final String PKDF2_HMAC_SHA1 = "PBKDF2WithHmacSHA1";
    private static final String ENCRYPTION_ALGORITHM = "AES";

    @Test
    public void generateKey() {

        byte[] pin = new byte[16];
        byte[] salt = new byte[16];
        SecretKey secretKey = KeyManager.keyDerivationBasedOnPBE(pin, salt, PKDF2_HMAC_SHA1, ENCRYPTION_ALGORITHM, 1000, 512);
        Assert.assertNotNull(secretKey);
    }
}
