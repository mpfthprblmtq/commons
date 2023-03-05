package com.mpfthprblmtq.commons.translation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mpfthprblmtq.commons.translation.model.Dictionary;
import com.mpfthprblmtq.commons.utils.CollectionUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;

import java.io.File;
import java.nio.file.Files;

public class DictionaryUtils {

    /**
     * Initializes the dictionary map with values from JSON or YAML
     * @param dictionaryFile the file to read
     * @return a populated Dictionary object
     */
    public static Dictionary initializeDictionary(File dictionaryFile) throws Exception {

        // initialize the main dictionary
        Dictionary dictionary = getDictionary(dictionaryFile);

        // check if we have any subDictionaries
        if (CollectionUtils.isNotEmpty(dictionary.getSubDictionaryPaths())) {
            for (String subDictionaryFilePath : dictionary.getSubDictionaryPaths()) {
                Dictionary subDictionary = initializeDictionary(new File(subDictionaryFilePath));
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

        // create dictionary
        return mapper.readValue(fileContents, Dictionary.class);
    }
}
