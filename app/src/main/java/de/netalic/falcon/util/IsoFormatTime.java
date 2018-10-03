package de.netalic.falcon.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class IsoFormatTime {

    public static String nowToIso() {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String now = simpleDateFormat.format(date);
        return now;
    }

    public static String lastWeekToIso() {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date sevenDaysBeforeDate = calendar.getTime();

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String lastWeek = simpleDateFormat.format(sevenDaysBeforeDate);
        return lastWeek;
    }

    public static String lastMonthToIso() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        Date thirtyBeforeDate = calendar.getTime();

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String lastMonth = simpleDateFormat.format(thirtyBeforeDate);
        return lastMonth;
    }
}
