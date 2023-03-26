package com.mpfthprblmtq.commons.translation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mpfthprblmtq.commons.translation.model.Dictionary;
import com.mpfthprblmtq.commons.translation.model.exception.InvalidTranslationFileException;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DictionaryUtils {

    /**
     * Initializes the dictionary map with values from JSON or YAML
     * @param dictionaryFilePath the file to read
     * @return a populated Dictionary object
     * @throws IOException if there's problems reading in the translation file
     */
    public static Dictionary initializeDictionary(String dictionaryFilePath) throws IOException {

        // initialize the main dictionary
        Dictionary dictionary = getDictionary(dictionaryFilePath);

        // check if we have any subDictionaries
        if (CollectionUtils.isNotEmpty(dictionary.getSubDictionaryPaths())) {
            for (String subDictionaryFilePath : dictionary.getSubDictionaryPaths()) {
                Dictionary subDictionary = initializeDictionary(subDictionaryFilePath);
                dictionary.getDictionaryValues().putAll(subDictionary.getDictionaryValues());
            }
        }

        return dictionary;
    }

    /**
     * Parses through the file given and returns a Dictionary object based on the file's contents
     * @param dictionaryFilePath the file to parse through
     * @return a Dictionary object
     */
    private static Dictionary getDictionary(String dictionaryFilePath) throws IOException {
        // initialize jackson mapper based on file type
        ObjectMapper mapper;
        if (dictionaryFilePath.endsWith(".yaml")) {
            mapper = new ObjectMapper(new YAMLFactory());
        } else {
            mapper = new ObjectMapper();
        }

        // confirm file's existence
        URL filePathUrl = DictionaryUtils.class.getResource(dictionaryFilePath);
        if (filePathUrl == null) {
            throw new FileNotFoundException("Couldn't find file: " + dictionaryFilePath);
        }

        // read in file contents
        Path filePath = Paths.get(filePathUrl.getPath());
        String fileContents = new String(Files.readAllBytes(filePath));

        // verify that file is not empty
        if (StringUtils.isEmpty(fileContents)) {
            throw new InvalidTranslationFileException("File contents are empty: " + dictionaryFilePath);
        }

        // create dictionary
        return mapper.readValue(fileContents, Dictionary.class);
    }
}
