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

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
