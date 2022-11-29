/*
 * Project: java-commons
 * File:    CollectionUtils.java
 * Desc:    Utility class with methods to help with collections
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.utils;

// imports
import java.util.*;

// class CollectionUtils
public class CollectionUtils {

    public static final List<?> EMPTY_LIST = new ArrayList<>();
    public static final Map<?,?> EMPTY_MAP = new HashMap<>();
    public static final Set<?> EMPTY_SET = new HashSet<>();

    /**
     * Null-safe check on if a collection is empty or null
     * @param collection the collection to check
     * @return the result of the check
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * Inverse of isEmpty(), will check if the collection is not empty and not null
     * @param collection the collection to check
     * @return the result of the check
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Creates a list with the given parameters.  If no elements are handed to it, will return a blank list.
     * @param list the elements to create the list from (comma delimited values)
     * @param <T> the generic object type to use for the list
     * @return a list of T objects
     */
    @SafeVarargs
    public static <T> List<T> createList(T... list) {
        if (list.length == 0) {
            return new ArrayList<>();
        } else if (list.length == 1) {
            return Collections.singletonList(list[0]);
        } else {
            return Arrays.asList(list);
        }
    }
}
