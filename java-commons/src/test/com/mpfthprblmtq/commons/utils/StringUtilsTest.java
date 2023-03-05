package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        // have to do this weird thing for the " " vs. NBSP thing
        double value3 = 56.78;
        String expected3 = "56,78";
        String actual = StringUtils.formatForCurrency(value3, Locale.forLanguageTag("de-DE"));
        assertTrue(actual.startsWith(expected3) && actual.endsWith("€"));
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

    @Test
    public void testTruncateWithEllipses_returnsTruncatedString() {
        String tested = "This is a really long string, and should probably be shortened.";
        String result = StringUtils.truncateWithEllipses(tested, 20);
        assertEquals(20, result.length());
        assertTrue(result.endsWith("..."));
    }

    @Test
    public void testTruncateWithEllipses_returnsGivenStringIfLessThanLength() {
        String tested = "Hello World!";
        String result = StringUtils.truncateWithEllipses(tested, 20);
        assertEquals(tested, result);
    }

    @Test
    public void testTruncateWithEllipsesTrailing_returnsGivenStringIfLessThanLength() {
        String tested = "Hello World!";
        String result = StringUtils.truncateWithEllipsesTrailing(tested, 20);
        assertEquals(tested, result);
    }

    @Test
    public void testTruncateWithEllipses_returnsNullIfGivenNull() {
        String result = StringUtils.truncateWithEllipses(null, 20);
        assertNull(result);
    }

    @Test
    public void testTruncateWithEllipses_returnsGivenStringIfLengthIsLessThanThree() {
        String tested = "Hi!";
        String result = StringUtils.truncateWithEllipses(tested, 20);
        assertEquals(tested, result);
    }

    @Test
    public void testTruncateWithEllipsesTrailing_returnsTruncatedString() {
        String tested = "This is a really long string, and should probably be shortened.";
        String result = StringUtils.truncateWithEllipsesTrailing(tested, 20);
        assertEquals(20, result.length());
        assertTrue(result.startsWith("..."));
    }

    @Test
    public void testTruncateWithEllipsesTrailing_returnsNullIfGivenNull() {
        String result = StringUtils.truncateWithEllipsesTrailing(null, 20);
        assertNull(result);
    }

    @Test
    public void testTruncateWithEllipsesTrailing_returnsGivenStringIfLengthIsLessThanThree() {
        String tested = "Hi!";
        String result = StringUtils.truncateWithEllipsesTrailing(tested, 20);
        assertEquals(tested, result);
    }
}