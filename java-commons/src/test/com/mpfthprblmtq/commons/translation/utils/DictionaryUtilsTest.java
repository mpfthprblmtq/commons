package com.mpfthprblmtq.commons.translation.utils;

import com.mpfthprblmtq.commons.translation.model.Dictionary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DictionaryUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"yaml", "json"})
    public void testInitializeDictionary_givenTranslationFileWithNoSubDirectories_createsDictionary(String fileType) throws Exception {
        Dictionary dictionary = DictionaryUtils.initializeDictionary(
                "/resources/es-MX/es-MX." + fileType);
        assertNotNull(dictionary);
        assertEquals(1, dictionary.getDictionaryValues().size());
        assertEquals("¡Hola Mundo!", dictionary.getDictionaryValues().get("HELLO_WORLD"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"yaml", "json"})
    public void testInitializeDictionary_givenTranslationFileWithOneNestedSubDirectory_createsDictionary(String fileType) throws Exception {
        Dictionary dictionary = DictionaryUtils.initializeDictionary(
                "/resources/de-DE/de-DE." + fileType);
        assertNotNull(dictionary);
        assertEquals(3, dictionary.getDictionaryValues().size());
        assertEquals("Hallo Welt!", dictionary.getDictionaryValues().get("HELLO_WORLD"));
        assertEquals("Auf Wiedersehen Welt!", dictionary.getDictionaryValues().get("GOODBYE_WORLD"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"yaml", "json"})
    public void testInitializeDictionary_givenTranslationFileWithThreeNestedSubDirectories_createsDictionary(String fileType) throws Exception {
        Dictionary dictionary = DictionaryUtils.initializeDictionary(
                "/resources/en-US/en-US." + fileType);
        assertNotNull(dictionary);
        assertEquals(3, dictionary.getDictionaryValues().size());
        assertEquals("Hello World!", dictionary.getDictionaryValues().get("HELLO_WORLD"));
        assertEquals("Goodbye World!", dictionary.getDictionaryValues().get("GOODBYE_WORLD"));
        assertEquals("Good Morning World!", dictionary.getDictionaryValues().get("GOOD_MORNING"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"yaml", "json"})
    public void testInitializeDictionary_givenEmptyTranslationFile_throwsException(String fileType) {
        String emptyFile = "/resources/fr-FR/fr-FR." + fileType;
        Exception expectedThrown = assertThrows(
                Exception.class,
                () -> DictionaryUtils.initializeDictionary(emptyFile),
                "Expected Exception, but not thrown.");
        assertTrue(expectedThrown.getMessage().contentEquals("File contents are empty: " + emptyFile));
    }

    @Test
    public void testInitializeDictionary_givenNonExistentFile_throwsException() {
        String badFile = "/resources/fr_FR/fr_FR.yaml";
        FileNotFoundException expectedThrown = assertThrows(
                FileNotFoundException.class,
                () -> DictionaryUtils.initializeDictionary(badFile),
                "Expected FileNotFoundException, but not thrown.");
        assertTrue(expectedThrown.getMessage().contentEquals("Couldn't find file: " + badFile));
    }

}