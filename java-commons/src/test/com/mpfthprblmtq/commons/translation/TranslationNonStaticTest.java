package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TranslationNonStaticTest {

    @Test
    public void testTranslationConstructor_whenGivenNonJsonOrYamlFile_thenThrowsCustomException() {
        String badFile = "/test/badFile.txt";
        FileTypeNotSupportedException expectedThrown = assertThrows(
                FileTypeNotSupportedException.class,
                () -> new TranslationNonStatic(Collections.singletonList(badFile)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Invalid translation file given, must be of type JSON or YAML: " + badFile));
    }

    @Test
    public void testTranslationConstructor_whenGivenBlankString_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> new TranslationNonStatic(Collections.singletonList(StringUtils.EMPTY)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Null or empty translation file path given!"));
    }

    @Test
    public void testTranslationConstructor_whenGivenNull_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> new TranslationNonStatic(null),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("No translation files given!"));
    }

    @Test
    public void testConstructor_whenGivenOneLocale_thenTranslates_JSON() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.json"));
        assertEquals(translationNonStatic.t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates_JSON() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        translationNonStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationNonStatic.t("HELLO_WORLD"));
    }

    @Test
    public void testConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate_JSON() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        assertEquals("HELLO_WORLD", translationNonStatic.t("HELLO_WORLD"));
    }

    @Test
    public void testConstructor_whenGivenOneLocale_thenTranslates() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals(translationNonStatic.t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        translationNonStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationNonStatic.t("HELLO_WORLD"));
    }

    @Test
    public void testConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals("HELLO_WORLD", translationNonStatic.t("HELLO_WORLD"));
    }

    @Test
    public void testConstructor_whenGivenTwoLocales_andLocaleIsSet_andThenChanged_thenTranslatesWithNewLocale() throws Exception {
        TranslationNonStatic translationNonStatic = new TranslationNonStatic(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        translationNonStatic.setCurrentLocale(Locale.forLanguageTag("en-US"));
        assertEquals("Hello World!", translationNonStatic.t("HELLO_WORLD"));
        translationNonStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationNonStatic.t("HELLO_WORLD"));
    }

}