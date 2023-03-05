package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TranslationInstanceTest {

    @Test
    public void testTranslationInstanceConstructor_whenGivenNonJsonOrYamlFile_thenThrowsCustomException() {
        String badFile = "/test/badFile.txt";
        FileTypeNotSupportedException expectedThrown = assertThrows(
                FileTypeNotSupportedException.class,
                () -> new TranslationInstance(Collections.singletonList(badFile)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Invalid translation file given, must be of type JSON or YAML: " + badFile));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenBlankString_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> new TranslationInstance(Collections.singletonList(StringUtils.EMPTY)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Null or empty translation file path given!"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenNull_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> new TranslationInstance(null),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("No translation files given!"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenOneLocale_thenTranslates_JSON() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.json"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates_JSON() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate_JSON() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("./src/test/resources/en_US/en_US.json", "./src/test/resources/de_DE/de_DE.json"));
        assertEquals("HELLO_WORLD", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenOneLocale_thenTranslates() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals(translationInstance.t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        assertEquals("HELLO_WORLD", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_andThenChanged_thenTranslatesWithNewLocale() throws Exception {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("./src/test/resources/en_US/en_US.yaml", "./src/test/resources/de_DE/de_DE.yaml"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("en-US"));
        assertEquals("Hello World!", translationInstance.t("HELLO_WORLD"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

}