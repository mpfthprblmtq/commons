package com.mpfthprblmtq.commons.translation;

import com.mpfthprblmtq.commons.translation.model.Dictionary;
import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.translation.utils.DictionaryUtils;
import com.mpfthprblmtq.commons.translation.utils.LocaleUtils;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
@NoArgsConstructor
public class TranslationInstance {

    Locale currentLocale;
    Map<Locale, Dictionary> dictionaries;

    public TranslationInstance(List<String> translationFiles) throws Exception {
        // check to see if we actually have translations or not
        if (CollectionUtils.isEmpty(translationFiles)) {
            throw new Exception("No translation files given!");
        }

        // initialize map
        setDictionaries(new HashMap<>());

        // traverse the list and validate each one, then create a dictionary file
        for (String translationFile : translationFiles) {
            // validate that it's not empty or null
            if (StringUtils.isEmpty(translationFile)) {
                throw new Exception("Null or empty translation file path given!");
            }

            // validate that it's the right file type
            if (!translationFile.endsWith(".json") && !translationFile.endsWith(".yaml")) {
                throw new FileTypeNotSupportedException(
                        "Invalid translation file given, must be of type JSON or YAML: " + translationFile);
            }

            // initialize locale
            Locale locale = LocaleUtils.getLocaleFromFilePath(translationFile);

            // if we only have one locale, then let's just use that one
            if (translationFiles.size() == 1) {
                setCurrentLocale(locale);
                getDictionaries().put(locale, DictionaryUtils.initializeDictionary(new File(translationFile)));
            } else {
                // more than one locale, fill the map
                getDictionaries().put(locale, DictionaryUtils.initializeDictionary(new File(translationFile)));
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

    public String t(String key, String... replacements) {
        throw new UnsupportedOperationException("Feature not implemented yet!");
    }

    private boolean preValidate(String key) {
        return getCurrentLocale() != null && getDictionaries().get(getCurrentLocale()).getDictionaryValues().containsKey(key);
    }

    private Map<String, String> getActiveDictionaryValues() {
        return getDictionaries().get(getCurrentLocale()).getDictionaryValues();
    }
}
