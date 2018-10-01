package de.netalic.falcon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String isoToDate(String isoDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        Date baseDate = null;
        try {
            baseDate = dateFormat.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return date.format(baseDate);
    }

    public static String isoToTime(String isoDate) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
        Date baseDate = null;
        try {
            baseDate = dateFormat.parse(isoDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat time = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return time.format(baseDate);
    }

}
