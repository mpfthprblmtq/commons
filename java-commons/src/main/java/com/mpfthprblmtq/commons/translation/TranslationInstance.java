package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.Dictionary;
import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.translation.model.exception.InvalidTranslationFileException;
import com.mpfthprblmtq.commons.translation.model.exception.NotEnoughSubstitutionValuesException;
import com.mpfthprblmtq.commons.translation.model.exception.TooManySubstitutionValuesException;
import com.mpfthprblmtq.commons.translation.utils.DictionaryUtils;
import com.mpfthprblmtq.commons.translation.utils.LocaleUtils;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.RegexUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@NoArgsConstructor
public class TranslationInstance {

    Locale currentLocale;
    Map<Locale, Dictionary> dictionaries;

    /**
     * Constructor that initializes our dictionaries.  Attempts to intelligently grab the locale from the given file
     * path, then populates the dictionaries field with the result of reading in the file.
     * @param translationFiles the list of files that contain the translation information
     */
    public TranslationInstance(List<String> translationFiles) {
        // check to see if we actually have translations or not
        if (CollectionUtils.isEmpty(translationFiles)) {
            throw new InvalidTranslationFileException("No translation files given!");
        }

        // initialize map
        setDictionaries(new HashMap<>());

        // traverse the list and validate each one, then create a dictionary file
        for (String translationFile : translationFiles) {
            // validate that it's not empty or null
            if (StringUtils.isEmpty(translationFile)) {
                throw new InvalidTranslationFileException("Null or empty translation file path given!");
            }

            // validate that it's the right file type
            if (!translationFile.endsWith(".json") && !translationFile.endsWith(".yaml")) {
                throw new FileTypeNotSupportedException(
                        "Invalid translation file given, must be of type JSON or YAML: " + translationFile);
            }

            // initialize locale
            Locale locale = LocaleUtils.getLocaleFromFilePath(translationFile);

            try {
                // if we only have one locale, then let's just use that one
                if (translationFiles.size() == 1) {
                    setCurrentLocale(locale);
                    getDictionaries().put(locale, DictionaryUtils.initializeDictionary(new File(translationFile)));
                } else {
                    // more than one locale, fill the map
                    getDictionaries().put(locale, DictionaryUtils.initializeDictionary(new File(translationFile)));
                }
            } catch (IOException e) {
                throw new InvalidTranslationFileException(e.getMessage());
            }

        }
    }

    /**
     * Basic translate function, takes a key and gives you the value
     * @param key the key to search for
     * @return the value of that key in the translation map, or the key itself if the value isn't in the map
     */
    public String t(String key) {
        return preValidate(key) ? getActiveDictionaryValues().get(key) : key;
    }

    /**
     * Slightly more complex translate function, takes a key and gives you the value, then does a string replacement
     * for the values you give it in the {} characters
     * @param key the key to search for
     * @param replacements the strings to replace in the translated string
     * @return the value of that key in the translation map with substitutions, or the key itself if the value isn't in
     * the map
     * @param <T> as long as whatever this class is has a toString() method
     */
    @SafeVarargs
    public final <T> String t(String key, T... replacements) {
        // check if we have the key in the translation map first by translating and checking value against key
        String translated = t(key);
        if (translated.equals(key)) {
            return translated;
        }

        // then validate on our substitutions
        if (preValidate(translated, replacements.length)) {
            // replace occurrences of the {} regex with our substitutions sequentially
            for (T replacement : replacements) {
                translated = translated.replaceFirst("\\{}", String.valueOf(replacement));
            }
        }

        // return the result
        return translated;
    }

    /**
     * Validates a translation key by first checking if we're working with an active Locale, then checks to see if
     * that value exists in the current working dictionary map
     * @param key the key to check for
     * @return the result of the check
     */
    private boolean preValidate(String key) {
        return getCurrentLocale() != null && getDictionaries().get(getCurrentLocale()).getDictionaryValues().containsKey(key);
    }

    /**
     * Overloaded validate function that takes the translated string and the number of substitutions, then verify that
     * we have enough parameters to substitute
     * @param translated the translated text
     * @param substitutions the number of substitutions we're working with
     * @return the result of the check
     */
    private boolean preValidate(String translated, int substitutions) {
        int occurrences = RegexUtils.countOccurrencesOfMatch(translated, "\\{}");
        if (occurrences > substitutions) {
            throw new NotEnoughSubstitutionValuesException(translated, occurrences, substitutions);
        } else if (occurrences < substitutions) {
            throw new TooManySubstitutionValuesException(translated, occurrences, substitutions);
        } else {
            return true;
        }
    }

    /**
     * Helper method to get the current working dictionary map
     * @return the current working dictionary map
     */
    private Map<String, String> getActiveDictionaryValues() {
        return getDictionaries().get(getCurrentLocale()).getDictionaryValues();
    }
}
