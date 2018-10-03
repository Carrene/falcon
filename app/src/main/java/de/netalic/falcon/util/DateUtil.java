package de.netalic.falcon.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String dateToIso(Date date) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return simpleDateFormat.format(date);
    }

    public static String nowToIso() {

        Date date = new Date(System.currentTimeMillis());
        return dateToIso(date);
    }

    public static String lastDayToIso() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date date = calendar.getTime();
        return dateToIso(date);
    }

    public static String lastWeekToIso() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date date = calendar.getTime();
        return dateToIso(date);
    }

    public static String lastMonthToIso() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date date = calendar.getTime();
        return dateToIso(date);
    }
}
