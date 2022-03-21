package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    // empty constant
    public static String EMPTY = "";

    /**
     * Checks if the string is null, blank, and doesn't have a value
     * @param s the string to check
     * @return the result of the check
     */
    public static boolean isEmpty(String s) {
        return s == null || s.equals(EMPTY);
    }

    /**
     * Checks if the string is NOT null, blank, and has a value
     * Basically just an inverse of the isEmpty function
     * @param s the string to check
     * @return the result of the check
     */
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    /**
     * Formats a number with commas
     * @param number the number to format
     * @return a formatted number
     */
    public static String formatNumber(int number) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(number);
    }

    /**
     * Pads zeros to the left of the number
     * @param value the number to pad
     * @param paddingLength the TOTAL length that the new string should be
     * @return the padded number
     */
    public static String padZeros(int value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }

    /**
     * Formats a number for currency
     * This overload doesn't take a locale, so use USD by default
     * @param value the double to format
     * @return the formatted currency amount
     */
    public static String formatForCurrency(double value) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return defaultFormat.format(value);
    }

    /**
     * Formats a number for currency
     * This overload takes a locale so we can use different locales
     * @param value the double to format
     * @param locale the locale to format with
     * @return the formatted currency amount
     */
    public static String formatForCurrency(double value, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }
}
