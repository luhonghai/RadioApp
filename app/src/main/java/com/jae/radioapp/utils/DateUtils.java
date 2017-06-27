package com.jae.radioapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alex on 6/27/17.
 */

public class DateUtils {

    /**
     * Convert from string to date
     * @param stringDate in type of yyyyMMddHHmmss 20170627050000
     * @return
     */
    public static Date fromStringToDate(String stringDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = simpleDateFormat.parse(stringDate);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert from date to string in type of HH:mm
     * @param date
     * @return
     */
    public static String fromDateToString(Date date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String dateString = simpleDateFormat.format(date);
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert yyyyMMddHHmmss to HH:mm
     * @param dateString
     * @return
     */
    public static String convertDate(String dateString) {
        Date date = fromStringToDate(dateString);
        if (date != null) {
            String convertedDateString = fromDateToString(date);
            if (convertedDateString == null) return dateString;
            else return convertedDateString;
        } else {
            return dateString;
        }
    }
}
