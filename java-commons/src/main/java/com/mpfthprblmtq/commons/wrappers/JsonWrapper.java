/*
 * Project: java-commons
 * File:    JsonWrapper.java
 * Desc:    Wrapper class used to create ObjectMappers and allows you to configure them
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.wrappers;

// imports
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

// class JsonWrapper
public class JsonWrapper {

    // constant fields used for setting attributes in ObjectMappers
    public static final int AUTO_CLOSE_SOURCE = 1;
    public static final int ALLOW_COMMENTS = 2;
    public static final int ALLOW_YAML_COMMENTS = 3;
    public static final int ALLOW_UNQUOTED_FIELD_NAMES = 4;
    public static final int ALLOW_SINGLE_QUOTES = 5;
    public static final int STRICT_DUPLICATE_DETECTION = 12;
    public static final int IGNORE_UNDEFINED = 13;
    public static final int INCLUDE_SOURCE_IN_LOCATION = 14;

    // main mapper object
    final ObjectMapper mapper;

    public JsonWrapper() {
        mapper = new ObjectMapper();
    }

    /**
     * Builder pattern to build a mapper with specific property
     * @param property the property to set
     * @param value the value of that property to set
     * @return the JsonWrapper being built
     */
    public JsonWrapper withProperty(int property, boolean value) {
        setProperty(property, value);
        return this;
    }

    /**
     * Sets the property based on the constant given
     * @param property the property to set
     * @param value the value of that property to set
     */
    public void setProperty(int property, boolean value) {
        switch (property) {
            case AUTO_CLOSE_SOURCE:
                mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, value);
                break;
            case ALLOW_COMMENTS:
                mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, value);
                break;
            case ALLOW_YAML_COMMENTS:
                mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, value);
                break;
            case ALLOW_UNQUOTED_FIELD_NAMES:
                mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, value);
                break;
            case ALLOW_SINGLE_QUOTES:
                mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, value);
                break;
            case STRICT_DUPLICATE_DETECTION:
                mapper.configure(JsonParser.Feature.STRICT_DUPLICATE_DETECTION, value);
                break;
            case IGNORE_UNDEFINED:
                mapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, value);
                break;
            case INCLUDE_SOURCE_IN_LOCATION:
                mapper.configure(JsonParser.Feature.INCLUDE_SOURCE_IN_LOCATION, value);
                break;
        }
    }
}
