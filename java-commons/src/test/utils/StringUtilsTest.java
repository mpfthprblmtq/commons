package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

class StringUtilsTest {

    @Test
    public void testFormatNumber() {
        Assertions.assertEquals("111", StringUtils.formatNumber(111));
        Assertions.assertEquals("2,222", StringUtils.formatNumber(2222));
        Assertions.assertEquals("33,333", StringUtils.formatNumber(33333));
        Assertions.assertEquals("444,444", StringUtils.formatNumber(444444));
        Assertions.assertEquals("5,555,555", StringUtils.formatNumber(5555555));
        Assertions.assertEquals("66,666,666", StringUtils.formatNumber(66666666));
        Assertions.assertEquals("777,777,777", StringUtils.formatNumber(777777777));
        Assertions.assertEquals("2,147,483,647", StringUtils.formatNumber(2147483647));

        Assertions.assertEquals("-111", StringUtils.formatNumber(-111));
        Assertions.assertEquals("-2,222", StringUtils.formatNumber(-2222));
        Assertions.assertEquals("-33,333", StringUtils.formatNumber(-33333));
        Assertions.assertEquals("-444,444", StringUtils.formatNumber(-444444));
        Assertions.assertEquals("-5,555,555", StringUtils.formatNumber(-5555555));
        Assertions.assertEquals("-66,666,666", StringUtils.formatNumber(-66666666));
        Assertions.assertEquals("-777,777,777", StringUtils.formatNumber(-777777777));
        Assertions.assertEquals("-2,147,483,647", StringUtils.formatNumber(-2147483647));
    }

    @Test
    @SuppressWarnings("all") // for the "always true" warning
    public void testIsEmpty() {
        Assertions.assertTrue(StringUtils.isEmpty(""));
        Assertions.assertTrue(StringUtils.isEmpty(null));
        Assertions.assertFalse(StringUtils.isEmpty("x"));
    }

    @Test
    @SuppressWarnings("all") // for the "always true" warning
    public void testIsNotEmpty() {
        Assertions.assertTrue(StringUtils.isNotEmpty("x"));
        Assertions.assertFalse(StringUtils.isNotEmpty(""));
        Assertions.assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void testPadZeros() {
        int value = 76;
        String expected = "76";
        Assertions.assertEquals(expected, StringUtils.padZeros(value, 2));

        expected = "0076";
        Assertions.assertEquals(expected, StringUtils.padZeros(value, 4));
    }

    @Test
    public void testFormatForCurrency() {
        double value1 = 12.34;
        String expected1 = "$12.34";
        Assertions.assertEquals(expected1, StringUtils.formatForCurrency(value1));

        double value2 = 12.3;
        String expected2 = "$12.30";
        Assertions.assertEquals(expected2, StringUtils.formatForCurrency(value2));

        double value3 = 56.78;
        String expected3 = "56,78 €";
        Assertions.assertEquals(expected3, StringUtils.formatForCurrency(value3, new Locale("de",  "DE")));
    }
}