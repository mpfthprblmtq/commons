package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.mpfthprblmtq.commons.translation.Translation.t;
import static org.junit.jupiter.api.Assertions.*;

class TranslationTest {

    @AfterEach
    public void afterEach() {
        Translation.getTranslationInstance().setCurrentLocale(null);
        Translation.getTranslationInstance().setDictionaries(null);
    }

    @Test
    public void testTranslationInitialize_whenGivenNonJsonOrYamlFile_thenThrowsCustomException() {
        String badFile = "/test/badFile.txt";
        FileTypeNotSupportedException expectedThrown = assertThrows(
                FileTypeNotSupportedException.class,
                () -> Translation.initializeTranslationFiles(Collections.singletonList(badFile)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Invalid translation file given, must be of type JSON or YAML: " + badFile));
    }

    @Test
    public void testTranslationInitialize_whenGivenBlankString_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> Translation.initializeTranslationFiles(Collections.singletonList(StringUtils.EMPTY)),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Null or empty translation file path given!"));
    }

    @Test
    public void testTranslationInitialize_whenGivenNull_thenThrowsException() {
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> Translation.initializeTranslationFiles(null),
                "Expected FileTypeNotSupportedException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("No translation files given!"));
    }

    @Test
    public void testTranslationInitialize_whenGivenOneLocale_thenTranslates_JSON() {
        Translation.initializeTranslationFiles(
                Collections.singletonList("/resources/de-DE/de-DE.json"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testTranslationInitialize_whenGivenTwoLocales_andLocaleIsSet_thenTranslates_JSON() {
        Translation.initializeTranslationFiles(
                Arrays.asList("/resources/en-US/en-US.json", "/resources/de-DE/de-DE.json"));
        Translation.getTranslationInstance().setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInitialize_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate_JSON() {
        Translation.initializeTranslationFiles(
                Arrays.asList("/resources/en-US/en-US.json", "/resources/de-DE/de-DE.json"));
        assertEquals("HELLO_WORLD", t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInitialize_whenGivenOneLocale_thenTranslates() {
        Translation.initializeTranslationFiles(
                Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testTranslationInitialize_whenGivenTwoLocales_andLocaleIsSet_thenTranslates() {
        Translation.initializeTranslationFiles(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        Translation.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInitialize_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate() {
        Translation.initializeTranslationFiles(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        assertEquals("HELLO_WORLD", t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInitialize_whenGivenTwoLocales_andLocaleIsSet_andThenChanged_thenTranslatesWithNewLocale() {
        Translation.initializeTranslationFiles(Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        Translation.setCurrentLocale(Locale.forLanguageTag("en-US"));
        assertEquals(t("HELLO_WORLD"), "Hello World!");
        Translation.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals(t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_thenTranslatesWithTextReplacement() throws Exception {
        Translation.initializeTranslationFiles(Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        String result = t("I_HAVE", 3, "Bananen");
        assertEquals("Ich habe 3 Bananen.", result);
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_andNoValueForKeyGiven_thenReturnsKey() {
        Translation.initializeTranslationFiles(Collections.singletonList("/resources/en-US/en-US.yaml"));
        assertEquals("BAD_KEY", t("BAD_KEY", new Object()));
    }

    @Test
    public void testGetSupportedLocales_whenTwoLocalesConfigured_thenReturnsListOfBothLocales() {
        Translation.initializeTranslationFiles(Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        List<Locale> expectedLocales = CollectionUtils.createList(Locale.forLanguageTag("en-US"), Locale.forLanguageTag("de-DE"));
        assertEquals(expectedLocales, Translation.getSupportedLocales());
    }

    @Test
    public void testGetSupportedLocales_whenNoLocalesConfigured_thenReturnsEmptyList() {
        TranslationInstance translationInstance = new TranslationInstance();
        assertEquals(CollectionUtils.EMPTY_LIST, translationInstance.getSupportedLocales());
    }
}