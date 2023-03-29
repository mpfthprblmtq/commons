package com.mpfthprblmtq.commons.translation;

import java.util.List;
import java.util.Locale;

public final class Translation {

    private static TranslationInstance translationInstance;

    /**
     * Getter method for the translationInstance.  If it is null, then creates a new instance.
     * @return a new Translation instance
     */
    public static TranslationInstance getTranslationInstance() {
        if (translationInstance == null) {
            translationInstance = new TranslationInstance();
        }
        return translationInstance;
    }

    /**
     * Initializer method to create main translation dictionaries
     * @param translationFiles a list of file paths with dictionary values
     */
    public static void initializeTranslationFiles(List<String> translationFiles) {
        translationInstance = new TranslationInstance(translationFiles);
    }

    /**
     * Sets the current locale on the translation instance
     * @param locale the locale to set
     */
    public static void setCurrentLocale(Locale locale) {
        getTranslationInstance().setCurrentLocale(locale);
    }

    /**
     * Returns the list of supported Locales
     * @return the list of supported Locales
     */
    public static List<Locale> getSupportedLocales() {
        return translationInstance.getSupportedLocales();
    }

    /**
     * Basic translate function, takes a key and gives you the value after validating first
     * @param key the key to search for
     * @return the value of that key in the translation map, or the key itself if the value isn't in the map
     */
    public static String t(String key) {
        return translationInstance.t(key);
    }

    /**
     * Slightly more complex translate function, takes a key and gives you the value, then does a string replacement
     * for the values you give it in the {} characters
     * @param key the key to search for
     * @param replacements the strings to replace in the translated string
     * @param <T> as long as whatever this class is has a toString() method
     * @return the value of that key in the translation map with substitutions, or the key itself if the value isn't in
     * the map
     */
    @SafeVarargs
    public static <T> String t(String key, T... replacements) {
        return translationInstance.t(key, replacements);
    }

}
