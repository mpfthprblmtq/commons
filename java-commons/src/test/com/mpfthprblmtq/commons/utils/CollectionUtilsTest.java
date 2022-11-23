package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isEmpty(null)' is always 'true'" warning
    public void testIsEmpty() {
        assertTrue(CollectionUtils.isEmpty(CollectionUtils.EMPTY_LIST));
        assertTrue(CollectionUtils.isEmpty(null));
        assertFalse(CollectionUtils.isEmpty(Arrays.asList("1", "2")));
    }

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isNotEmpty(null)' is always 'false'" warning
    public void isIsNotEmpty() {
        assertTrue(CollectionUtils.isNotEmpty(Arrays.asList("1", "2")));
        assertFalse(CollectionUtils.isNotEmpty(CollectionUtils.EMPTY_LIST));
        assertFalse(CollectionUtils.isNotEmpty(null));
    }

}