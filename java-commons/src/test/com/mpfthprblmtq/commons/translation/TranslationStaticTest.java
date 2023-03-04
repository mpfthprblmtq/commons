package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static com.mpfthprblmtq.commons.translation.TranslationStatic.t;
import static org.junit.jupiter.api.Assertions.*;

class TranslationStaticTest {

    @AfterEach
    public void afterEach() {
        TranslationStatic.setCurrentLocale(null);
        TranslationStatic.setDictionaries(null);
    }

    @Test
    public void testTranslationInitialize_whenGivenNonJsonOrYamlFile_thenThrowsCustomException() {
        String badFile = "/test/badFile.txt";
        FileTypeNotSupportedException expectedThrown = assertThrows(
                FileTypeNotSupportedException.class,
                () -> TranslationStatic.initializeTranslationFiles(Collections.singletonList(badFile)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Invalid translation file given, must be of type JSON or YAML: " + badFile));
    }

    @Test
    public void testTranslationInitialize_whenGivenBlankString_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> TranslationStatic.initializeTranslationFiles(Collections.singletonList(StringUtils.EMPTY)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Null or empty translation file path given!"));
    }

    @Test
    public void testTranslationInitialize_whenGivenNull_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> TranslationStatic.initializeTranslationFiles(null),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("No translation files given!"));
    }

    @Test
    public void testInitialize_whenGivenOneLocale_thenTranslates_JSON() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.json"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testInitialize_whenGivenTwoLocales_andLocaleIsSet_thenTranslates_JSON() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        TranslationStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", t("HELLO_WORLD"));
    }

    @Test
    public void testInitialize_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate_JSON() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        assertEquals("HELLO_WORLD", t("HELLO_WORLD"));
    }

    @Test
    public void testInitialize_whenGivenOneLocale_thenTranslates() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testInitialize_whenGivenTwoLocales_andLocaleIsSet_thenTranslates() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        TranslationStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", t("HELLO_WORLD"));
    }

    @Test
    public void testInitialize_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate() throws Exception {
        TranslationStatic.initializeTranslationFiles(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals("HELLO_WORLD", t("HELLO_WORLD"));
    }

    @Test
    public void testInitialize_whenGivenTwoLocales_andLocaleIsSet_andThenChanged_thenTranslatesWithNewLocale() throws Exception {
        TranslationStatic.initializeTranslationFiles(Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        TranslationStatic.setCurrentLocale(Locale.forLanguageTag("en-US"));
        assertEquals(t("HELLO_WORLD"), "Hello World!");
        TranslationStatic.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }
}