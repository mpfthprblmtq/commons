package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilsTest {

    Date TEST_DATE;

    @BeforeEach
    public void beforeEach() throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getDefault());
        TEST_DATE = isoFormat.parse("1994-03-23T12:00:00");
    }

    @Test
    public void testFormatSimpleDate() {
        String expected = "03-23-1994";
        String actual = DateUtils.formatSimpleDate(TEST_DATE);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatDetailedDateTime() {
        String expected = "03/23/1994 12:00:00";
        String actual = DateUtils.formatDetailedDateTime(TEST_DATE);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatCustomDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm");
        String expected = "03 23 1994 12:00";
        String actual = DateUtils.formatCustomDateTime(TEST_DATE, sdf);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSimpleDate() throws ParseException {
        String dateString = "03-23-1994";
        Date actualDate = DateUtils.getSimpleDate(dateString);

        // create some calendar instances so we can compare M/D/Y
        Calendar actualCalendar = Calendar.getInstance();
        actualCalendar.setTime(actualDate);
        Calendar expectedCalendar = Calendar.getInstance();
        expectedCalendar.setTime(TEST_DATE);

        // only assert M/D/Y since that's all this date format cares about
        assertEquals(expectedCalendar.get(Calendar.MONTH), actualCalendar.get(Calendar.MONTH));
        assertEquals(expectedCalendar.get(Calendar.DAY_OF_MONTH), actualCalendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(expectedCalendar.get(Calendar.YEAR), actualCalendar.get(Calendar.YEAR));
    }

    @Test
    public void testGetDetailedDate() throws ParseException {
        String dateTimeString = "03/23/1994 12:00:00";
        Date actualDate = DateUtils.getDetailedDateTime(dateTimeString);

        // we explicitly set the time in this test, so only one assert is necessary
        assertEquals(TEST_DATE, actualDate);
    }

    @Test
    public void testGetCustomDateTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd yyyy HH:mm");
        String dateTimeString = "03 23 1994 12:00";
        Date actualDate = DateUtils.getCustomDateTime(dateTimeString, sdf);

        // we explicitly set the time in this test, so only one assert is necessary
        assertEquals(TEST_DATE, actualDate);
    }

    @Test
    public void testIsDateSameAsToday_returnsFalseWithTestDate() {
        assertFalse(DateUtils.isDateSameAsToday(TEST_DATE));
    }

    @Test
    public void testIsDateSameAsToday_returnsTrueForTodaysDate() {
        assertTrue(DateUtils.isDateSameAsToday(new Date()));
    }

}