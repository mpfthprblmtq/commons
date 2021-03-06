package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class StringUtilsTest {

    @Test
    @SuppressWarnings("all") // for the "always true" warning
    public void testIsEmpty() {
        assertTrue(StringUtils.isEmpty(""));
        assertTrue(StringUtils.isEmpty(null));
        assertFalse(StringUtils.isEmpty("x"));
    }

    @Test
    @SuppressWarnings("all") // for the "always true" warning
    public void testIsNotEmpty() {
        assertTrue(StringUtils.isNotEmpty("x"));
        assertFalse(StringUtils.isNotEmpty(""));
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void testValidateString() {
        assertEquals(StringUtils.EMPTY, StringUtils.validateString(""));
        assertEquals(StringUtils.EMPTY, StringUtils.validateString(null));
        assertNotEquals(StringUtils.EMPTY, StringUtils.validateString("x"));
    }

    @Test
    public void testFormatNumber() {
        assertEquals("111", StringUtils.formatNumber(111));
        assertEquals("2,222", StringUtils.formatNumber(2222));
        assertEquals("33,333", StringUtils.formatNumber(33333));
        assertEquals("444,444", StringUtils.formatNumber(444444));
        assertEquals("5,555,555", StringUtils.formatNumber(5555555));
        assertEquals("66,666,666", StringUtils.formatNumber(66666666));
        assertEquals("777,777,777", StringUtils.formatNumber(777777777));
        assertEquals("2,147,483,647", StringUtils.formatNumber(2147483647));

        assertEquals("-111", StringUtils.formatNumber(-111));
        assertEquals("-2,222", StringUtils.formatNumber(-2222));
        assertEquals("-33,333", StringUtils.formatNumber(-33333));
        assertEquals("-444,444", StringUtils.formatNumber(-444444));
        assertEquals("-5,555,555", StringUtils.formatNumber(-5555555));
        assertEquals("-66,666,666", StringUtils.formatNumber(-66666666));
        assertEquals("-777,777,777", StringUtils.formatNumber(-777777777));
        assertEquals("-2,147,483,647", StringUtils.formatNumber(-2147483647));
    }

    @Test
    public void testPadZeros() {
        int value = 76;
        String expected = "76";
        assertEquals(expected, StringUtils.padZeros(value, 2));

        expected = "0076";
        assertEquals(expected, StringUtils.padZeros(value, 4));
    }

    @Test
    public void testFormatForCurrency() {
        double value1 = 12.34;
        String expected1 = "$12.34";
        assertEquals(expected1, StringUtils.formatForCurrency(value1));

        double value2 = 12.3;
        String expected2 = "$12.30";
        assertEquals(expected2, StringUtils.formatForCurrency(value2));

        double value3 = 56.78;
        String expected3 = "56,78 ???";
        assertEquals(expected3, StringUtils.formatForCurrency(value3, new Locale("de",  "DE")));
    }

    @Test
    public void testFindCommonString_findsCommonString() {
        List<String> strings = Arrays.asList("test1", "test2", "3test");
        String expected = "test";
        String actual = StringUtils.findCommonString(strings);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindCommonString_doesntFindCommonString() {
        List<String> strings = Arrays.asList("test1", "test2", "3test", "what", "how");
        String expected = StringUtils.EMPTY;
        String actual = StringUtils.findCommonString(strings);

        assertEquals(expected, actual);
    }

    @Test
    public void testCheckIfSame_returnsTrueForAllSameStrings() {
        List<String> strings = Arrays.asList("test", "test", "test");
        assertTrue(StringUtils.checkIfSame("test", strings));
    }

    @Test
    public void testCheckIfSame_returnsFalseForDifferentStrings() {
        List<String> strings = Arrays.asList("test1", "test2", "test3");
        assertFalse(StringUtils.checkIfSame("test1", strings));
    }

    @Test
    @SuppressWarnings("all")    // for the "always true" warning
    public void testEquals_returnsTrueForEqualStrings() {
        assertTrue(StringUtils.equals("test", "test"));
        assertTrue(StringUtils.equals(null, null));
    }

    @Test
    @SuppressWarnings("all")    // for the "always false" warning
    public void testEquals_returnsFalseForNull() {
        assertFalse(StringUtils.equals("test", null));
        assertFalse(StringUtils.equals(null, "test"));
    }

    @Test
    public void testEquals_returnsFalseForDifferentStrings() {
        assertFalse(StringUtils.equals("test", "test1"));
    }

    @Test
    @SuppressWarnings("all")    // for the "always true" warning
    public void testEqualsIgnoreCase_returnsTrueForEqualStrings() {
        assertTrue(StringUtils.equalsIgnoreCase("TEST", "test"));
        assertTrue(StringUtils.equalsIgnoreCase(null, null));
    }

    @Test
    @SuppressWarnings("all")    // for the "always false" warning
    public void testEqualsIgnoreCase_returnsFalseForNull() {
        assertFalse(StringUtils.equalsIgnoreCase("test", null));
        assertFalse(StringUtils.equalsIgnoreCase(null, "test"));
    }

    @Test
    public void testEqualsIgnoreCase_returnsFalseForDifferentStrings() {
        assertFalse(StringUtils.equalsIgnoreCase("test", "test1"));
    }
}