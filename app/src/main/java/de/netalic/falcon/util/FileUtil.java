package de.netalic.falcon.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class FileUtil {

    public static String readTextFromStream(InputStream inputStream) throws Exception {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
