package com.mpfthprblmtq.commons.translation;

import java.util.List;
import java.util.Locale;

public final class Translation {

    private static TranslationInstance translationInstance;

    /**
     * Getter method for the translationInstance.  If it is null, then creates a new instance.
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
     * @throws Exception if given null/empty list, given invalid file types
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
     * Basic translate function, takes a key and gives you the value after validating first
     * @param key the key to search for
     * @return the value of that key in the translation map, or the key itself if the value isn't in the map
     */
    public static String t(String key) {
        return translationInstance.t(key);
    }

    public static String t(String key, String... replacements) {
        return translationInstance.t(key, replacements);
    }

}
