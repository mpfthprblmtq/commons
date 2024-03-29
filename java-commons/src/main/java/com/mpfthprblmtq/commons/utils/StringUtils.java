/*
 * Project: java-commons
 * File:    StringUtils.java
 * Desc:    Utility class with methods to help with String processing
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.utils;

// imports
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

// class StringUtils
@SuppressWarnings("unused")
public class StringUtils {

    // constants
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String NEW_LINE = "\n";

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
     * Utility function to set a string to either "" or the string value (really just prevents null values)
     * @param s the string to check
     * @return the valid string
     */
    public static String validateString(String s) {
        return isEmpty(s) ? EMPTY : s;
    }

    /**
     * Utility function to set an object to either "" or the toString() value (really just prevents null values)
     * @param o the object to check
     * @return the string representation of the object
     */
    public static String validateString(Object o) {
        return o == null ? EMPTY : o.toString();
    }

    /**
     * Custom equals method to prevent NullPointerExceptions with String.equals()
     * @param string1 first string to check
     * @param string2 second string to check
     * @return the result of the check
     */
    @SuppressWarnings("all")
    public static boolean equals(String string1, String string2) {
        return string1 == null ? string2 == null : string1.equals(string2);
    }

    /**
     * Custom equalsIgnoreCase method to prevent NullPointerExceptions with String.equalsIgnoreCase()
     * @param string1 first string to check
     * @param string2 second string to check
     * @return the result of the check
     */
    public static boolean equalsIgnoreCase(String string1, String string2) {
        return string1 == null ? string2 == null : string1.equalsIgnoreCase(string2);
    }

    /**
     * Formats a number with default Locale
     * @param number the number to format
     * @param <T> the class of the number passed in, should be int, double, long, etc.
     * @return a formatted number
     */
    public static <T> String formatNumber(T number) {
        DecimalFormat df = new DecimalFormat("###,###,###.#######");
        return df.format(number);
    }

    /**
     * Formats a number based on Locale given
     * @param number the number to format
     * @param locale the locale to format with
     * @param <T> the class of the number passed in, should be int, double, long, etc.
     * @return a formatted number
     */
    public static <T> String formatNumber(T number, Locale locale) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        DecimalFormat format = new DecimalFormat("###,###,###.########", symbols);
        return format.format(number);
    }

    /**
     * Pads zeros to the left of the number
     * @param value the number to pad
     * @param paddingLength the TOTAL length that the new string should be
     * @param <T> the class of the number passed in, should be int, double, long, etc.
     * @return the padded number
     */
    public static <T> String padZeros(T value, int paddingLength) {
        return String.format("%0" + paddingLength + "d", value);
    }

    /**
     * Formats a number for currency
     * This overload doesn't take a locale, so use USD by default
     * @param value the double to format
     * @param <T> the class of the number passed in, should be int, double, long, etc.
     * @return the formatted currency amount
     */
    public static <T> String formatForCurrency(T value) {
        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return defaultFormat.format(value);
    }

    /**
     * Formats a number for currency
     * This overload takes a locale, so we can use different locales
     * @param value the double to format
     * @param locale the locale to format with
     * @param <T> the class of the number passed in, should be int, double, long, etc.
     * @return the formatted currency amount
     */
    public static <T> String formatForCurrency(T value, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }

    /**
     * Utility function, finds common longest string from list of strings
     * @param strings the list of strings
     * @return the longest common string
     */
    public static String findCommonString(List<String> strings) {

        // result string
        String result = "";

        // get first string from list as reference
        String s = strings.get(0);

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                // generating all possible substrings of our reference string strings.get(0) i.e s
                String stem = s.substring(i, j);
                int k;
                for (k = 1; k < strings.size(); k++) {
                    // check if the generated stem is common to all words
                    if (!strings.get(k).contains(stem))
                        break;
                }
                // if current substring is present in all strings and its length is greater than current result
                if (k == strings.size() && result.length() < stem.length())
                    result = stem;
            }
        }
        return result;
    }

    /**
     * Checks if a string is the same throughout a list
     * @param str the string to check
     * @param list the list of strings
     * @return the result of the check
     */
    public static boolean checkIfSame(String str, List<String> list) {
        for (String s : list) {
            if (!s.equals(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Truncates string at the end with ellipses (...)
     * @param s the string to truncate
     * @param length the max length the string can be
     * @return a truncated string
     */
    public static String truncateWithEllipses(String s, int length) {
        if (isEmpty(s) || s.length() < 3) {
            return s;
        }
        return s.length() < length ? s : s.substring(0, length - 3).concat("...");
    }

    /**
     * Truncates string at the beginning with ellipses (...)
     * @param s the string to truncate
     * @param length the max length the string can be
     * @return a truncated string
     */
    public static String truncateWithEllipsesTrailing(String s, int length) {
        if (isEmpty(s) || s.length() < 3) {
            return s;
        }
        return s.length() < length ? s : "...".concat(s.substring(s.length() - (length - 3)));
    }
}
