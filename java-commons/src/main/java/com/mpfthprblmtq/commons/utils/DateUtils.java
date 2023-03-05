/*
 * Project: java-commons
 * File:    DateUtils.java
 * Desc:    Utility class with methods to help with date processing
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.utils;

// imports
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

// class DateUtils
@SuppressWarnings("unused")
public class DateUtils {

    // constant date formats
    static final SimpleDateFormat DETAILED_DATETIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * Sets the timezone to the system default
     * @param dateFormat the dateformat to set the timezone on
     */
    public static void setTimezone(SimpleDateFormat dateFormat) {
        dateFormat.setTimeZone(TimeZone.getDefault());
    }

    /**
     * Sets the timezone given to the dateformat given
     * @param dateFormat the dateformat to set the timezone on
     * @param timezone the timezone to set to
     */
    public static void setTimezone(SimpleDateFormat dateFormat, String timezone) {
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    /**
     * Formats a Date object to a simple string
     *
     * @param date the date to format
     * @return the formatted date
     */
    public static String formatSimpleDate(Date date) {
        setTimezone(SIMPLE_DATE_FORMAT);
        return SIMPLE_DATE_FORMAT.format(date);
    }

    /**
     * Formats a Date object to a more complex string
     *
     * @param date the date to format
     * @return the formatted date
     */
    public static String formatDetailedDateTime(Date date) {
        setTimezone(DETAILED_DATETIME_FORMAT);
        return DETAILED_DATETIME_FORMAT.format(date);
    }

    /**
     * Formats a Date object to a custom string format
     *
     * @param date the date to format
     * @param sdf the SimpleDateFormat to format with
     * @return the formatted date
     */
    public static String formatCustomDateTime(Date date, SimpleDateFormat sdf) {
        setTimezone(sdf);
        return sdf.format(date);
    }

    /**
     * Gets a Date object from a simple string
     *
     * @param date the date string to parse
     * @throws ParseException if there's an issue parsing the date
     * @return a date object
     */
    public static Date getSimpleDate(String date) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(date);
    }

    /**
     * Gets a Date object from a more complex string
     *
     * @param date the date string to parse
     * @throws ParseException if there's an issue parsing the date
     * @return a date object
     */
    public static Date getDetailedDateTime(String date) throws ParseException {
        return DETAILED_DATETIME_FORMAT.parse(date);
    }

    /**
     * Gets a Date object from a custom string format
     *
     * @param date the date string to parse
     * @param sdf the SimpleDateFormat to parse with
     * @throws ParseException if there's an issue parsing the date with the custom format
     * @return a date object
     */
    public static Date getCustomDateTime(String date, SimpleDateFormat sdf) throws ParseException {
        return sdf.parse(date);
    }

    /**
     * Checks to see if a date is the same as another
     * @param date the date to compare to today
     * @return the result of the check
     */
    public static boolean isDateSameAsToday(Date date) {

        Calendar today = Calendar.getInstance();
        today.setTime(new Date());

        Calendar compare = Calendar.getInstance();
        compare.setTime(date);

        return today.get(Calendar.DAY_OF_MONTH) == compare.get(Calendar.DAY_OF_MONTH) &&
                today.get(Calendar.MONTH) == compare.get(Calendar.MONTH) &&
                today.get(Calendar.YEAR) == compare.get(Calendar.YEAR);
    }
}
