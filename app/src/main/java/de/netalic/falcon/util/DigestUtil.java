package de.netalic.falcon.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

    public static byte[] digestSha512(String value) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest();
        return digest;
    }
}
