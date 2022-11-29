package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
    public void testIsNotEmpty() {
        assertTrue(CollectionUtils.isNotEmpty(Arrays.asList("1", "2")));
        assertFalse(CollectionUtils.isNotEmpty(CollectionUtils.EMPTY_LIST));
        assertFalse(CollectionUtils.isNotEmpty(null));
    }

    @Test
    public void testCreateEmptyList() {
        List<String> emptyList = CollectionUtils.createList();
        assertTrue(CollectionUtils.isEmpty(emptyList));
    }

    @Test
    public void testCreateStringSingletonList() {
        List<String> singletonList = CollectionUtils.createList("1");
        assertTrue(CollectionUtils.isNotEmpty(singletonList));
        assertEquals(1, singletonList.size());
    }

    @Test
    public void testCreateStringList() {
        List<String> multipleElementList = CollectionUtils.createList("1", "deux", "three");
        assertTrue(CollectionUtils.isNotEmpty(multipleElementList));
        assertEquals(3, multipleElementList.size());
    }
}