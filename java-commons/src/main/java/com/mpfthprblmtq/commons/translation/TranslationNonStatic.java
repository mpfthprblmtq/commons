package com.mpfthprblmtq.commons.translation;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mpfthprblmtq.commons.translation.model.Dictionary;
import com.mpfthprblmtq.commons.translation.model.exception.FileTypeNotSupportedException;
import com.mpfthprblmtq.commons.translation.utils.LocaleUtils;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;
import lombok.Data;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Data
public class TranslationNonStatic {

    Locale currentLocale;
    Map<Locale, Dictionary> dictionaries;

    public TranslationNonStatic(List<String> translationFiles) throws Exception {
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
                getDictionaries().put(locale, initializeDictionary(new File(translationFile)));
            } else {
                // more than one locale, fill the map
                getDictionaries().put(locale, initializeDictionary(new File(translationFile)));
            }
        }
    }

    /**
     * Initializes the dictionary map with values from JSON or YAML
     * @param dictionaryFile the file to read
     * @return a populated Dictionary object
     */
    private static Dictionary initializeDictionary(File dictionaryFile) throws Exception {

        // initialize the main dictionary
        Dictionary dictionary = getDictionary(dictionaryFile);

        // check if we have any subDictionaries
        if (CollectionUtils.isNotEmpty(dictionary.getSubDictionaryPaths())) {
            for (String subDictionaryFilePath : dictionary.getSubDictionaryPaths()) {
                Dictionary subDictionary = getDictionary(new File(subDictionaryFilePath));
                dictionary.getDictionaryValues().putAll(subDictionary.getDictionaryValues());
            }
        }

        return dictionary;
    }

    /**
     * Parses through the file given and returns a Dictionary object based on the file's contents
     * @param dictionaryFile the file to parse through
     * @return a Dictionary object
     * @throws Exception if there's issues reading the file
     */
    private static Dictionary getDictionary(File dictionaryFile) throws Exception {
        // initialize jackson mapper based on file type
        ObjectMapper mapper;
        if (dictionaryFile.getName().endsWith(".yaml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            mapper = new ObjectMapper();
        }

        // read in file contents
        String fileContents = new String(Files.readAllBytes(dictionaryFile.toPath()));
        if (StringUtils.isEmpty(fileContents)) {
            throw new Exception("File contents are empty: " + dictionaryFile.getName());
        }

        // create dictionary and sub dictionaries if possible
        return mapper.readValue(fileContents, Dictionary.class);
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
