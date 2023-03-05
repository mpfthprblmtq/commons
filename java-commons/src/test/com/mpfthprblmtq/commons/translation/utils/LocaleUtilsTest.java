package com.mpfthprblmtq.commons.translation.utils;

import com.mpfthprblmtq.commons.translation.model.exception.InvalidLocaleException;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocaleUtilsTest {

    @Test
    public void testGetLocaleFromFilePath_whenGivenNull_thenReturnsNull() throws InvalidLocaleException {
        assertNull(LocaleUtils.getLocaleFromFilePath(null));
    }

    @Test
    public void testGetLocaleFromFilePath_whenGivenEmptyString_thenReturnsNull() throws InvalidLocaleException {
        assertNull(LocaleUtils.getLocaleFromFilePath(StringUtils.EMPTY));
    }

    @ParameterizedTest
    @ValueSource(strings = {"en-US", "de-DE", "es-MX"})
    public void testGetLocaleFromFilePath_whenGivenValidExactLocaleInPath_thenReturnsLocale(String locale) throws InvalidLocaleException {
        assertEquals(Locale.forLanguageTag(locale),
                LocaleUtils.getLocaleFromFilePath("./" + locale + "/" + locale + ".json"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"eng-USA", "deu-DEU", "spa-MEX"})
    public void testGetLocaleFromFilePath_whenGivenValidISO3LocaleInPath_thenReturnsLocale(String locale) throws InvalidLocaleException {
        String[] arr = locale.split("-");
        String language = arr[0];
        String country = arr[1];
        assertEquals(LocaleUtils.getStandardizedLocale(language, country),
                LocaleUtils.getLocaleFromFilePath("./" + locale + "/" + locale + ".json"));
    }



    @ParameterizedTest
    @ValueSource(strings = {"en_US", "de_DE", "es_MX"})
    public void testGetLocaleFromFilePath_whenGivenValidNonExactISO2LocaleInPath_thenReturnsLocale(String locale) throws InvalidLocaleException {
        String[] arr = locale.split("_");
        String language = arr[0];
        String country = arr[1];
        assertEquals(LocaleUtils.getStandardizedLocale(language, country),
                LocaleUtils.getLocaleFromFilePath("./" + locale + "/" + locale + ".json"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"xx-XX", "ab-CD"})
    public void testGetLocaleFromFilePath_whenGivenInvalidISO2Locale_thenThrowsException(String badLocale) {
        InvalidLocaleException expectedThrown = assertThrows(
                InvalidLocaleException.class,
                () -> LocaleUtils.getLocaleFromFilePath("./src/resources/" + badLocale + ".json"),
                "Expected InvalidLocaleException, but not thrown.");
        assertTrue(expectedThrown.getMessage().contentEquals("Invalid Locale given: " + badLocale));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc-XYZ"})
    public void testGetStandardizedLocale_whenGivenInvalidISO3Locale_thenThrowsException(String badLocale) {
        String[] arr = badLocale.split("-");
        String language = arr[0];
        String country = arr[1];
        InvalidLocaleException expectedThrown = assertThrows(
                InvalidLocaleException.class,
                () -> LocaleUtils.getStandardizedLocale(language, country),
                "Expected InvalidLocaleException, but not thrown.");
        assertTrue(expectedThrown.getMessage().contentEquals("Invalid Locale given: " + badLocale));
    }

    @Test
    public void testGetLocaleFromFilePath_whenCantInferFromFilePath_thenReturnNull() throws InvalidLocaleException {
        assertNull(LocaleUtils.getLocaleFromFilePath("./src/test/resources/languages.json"));
    }

}