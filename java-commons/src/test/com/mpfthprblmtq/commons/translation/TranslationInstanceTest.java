package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.translation.model.exception.InvalidTranslationFileException;
import com.mpfthprblmtq.commons.translation.model.exception.NotEnoughSubstitutionValuesException;
import com.mpfthprblmtq.commons.translation.model.exception.TooManySubstitutionValuesException;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    public void testTranslationInstanceConstructor_whenGivenFileThatDoesntExist_thenThrowsException() {
        String badFilePath = "/resources/en_US/en_US.yaml";
        Exception expectedThrown = assertThrows(
                InvalidTranslationFileException.class,
                () -> new TranslationInstance(Collections.singletonList(badFilePath)),
                "Expected InvalidTranslationFileException, but not thrown.");
        assertTrue(expectedThrown.getMessage()
                .contentEquals("Couldn't find file: " + badFilePath));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenOneLocale_thenTranslates_JSON() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/de-DE/de-DE.json"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates_JSON() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.json", "/resources/de-DE/de-DE.json"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate_JSON() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.json", "/resources/de-DE/de-DE.json"));
        assertEquals("HELLO_WORLD", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenOneLocale_thenTranslates() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        assertEquals(translationInstance.t("HELLO_WORLD"), "Hallo Welt!");
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_thenTranslates() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsNotSet_thenDoesntTranslate() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        assertEquals("HELLO_WORLD", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslationInstanceConstructor_whenGivenTwoLocales_andLocaleIsSet_andThenChanged_thenTranslatesWithNewLocale() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("en-US"));
        assertEquals("Hello World!", translationInstance.t("HELLO_WORLD"));
        translationInstance.setCurrentLocale(Locale.forLanguageTag("de-DE"));
        assertEquals("Hallo Welt!", translationInstance.t("HELLO_WORLD"));
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_thenTranslatesWithTextReplacement() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        String result = translationInstance.t("I_HAVE", 3, "Bananen");
        assertEquals("Ich habe 3 Bananen.", result);
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_andNotEnoughSubstitutionsGiven_thenThrowsException() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        NotEnoughSubstitutionValuesException expectedThrown = assertThrows(
                NotEnoughSubstitutionValuesException.class,
                () -> translationInstance.t("I_HAVE", 3),
                "Expected NotEnoughSubstitutionValuesException, but not thrown.");
        assertTrue(expectedThrown.getMessage().startsWith("Not enough substitution values given for given translation"));
        assertTrue(expectedThrown.getMessage().contains("Expected 2, but given 1"));
        assertTrue(expectedThrown.getMessage().endsWith("Ich habe {} {}."));
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_andTooManySubstitutionsGiven_thenThrowsException() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/de-DE/de-DE.yaml"));
        TooManySubstitutionValuesException expectedThrown = assertThrows(
                TooManySubstitutionValuesException.class,
                () -> translationInstance.t("I_HAVE", 3, "Bananen", "Extra"),
                "Expected TooManySubstitutionValuesException, but not thrown.");
        assertTrue(expectedThrown.getMessage().startsWith("Too many substitution values given for given translation"));
        assertTrue(expectedThrown.getMessage().contains("Expected 2, but given 3"));
        assertTrue(expectedThrown.getMessage().endsWith("Ich habe {} {}."));
    }

    @Test
    public void testTranslate_whenGivenTranslationWithSubstitutions_andNoValueForKeyGiven_thenReturnsKey() {
        TranslationInstance translationInstance = new TranslationInstance(
                Collections.singletonList("/resources/en-US/en-US.yaml"));
        assertEquals("BAD_KEY", translationInstance.t("BAD_KEY", new Object()));
    }

    @Test
    public void testGetSupportedLocales_whenTwoLocalesConfigured_thenReturnsListOfBothLocales() {
        TranslationInstance translationInstance = new TranslationInstance(
                Arrays.asList("/resources/en-US/en-US.yaml", "/resources/de-DE/de-DE.yaml"));
        List<Locale> expectedLocales = CollectionUtils.createList(Locale.forLanguageTag("en-US"), Locale.forLanguageTag("de-DE"));
        assertEquals(expectedLocales, translationInstance.getSupportedLocales());
    }

    @Test
    public void testGetSupportedLocales_whenNoLocalesConfigured_thenReturnsEmptyList() {
        TranslationInstance translationInstance = new TranslationInstance();
        assertEquals(CollectionUtils.EMPTY_LIST, translationInstance.getSupportedLocales());
    }

}