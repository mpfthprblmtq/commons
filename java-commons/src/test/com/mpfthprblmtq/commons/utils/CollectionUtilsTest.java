package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollectionUtilsTest {

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isEmpty(null)' is always 'true'" warning
    public void testIsEmptyCollection() {
        assertTrue(CollectionUtils.isEmpty(CollectionUtils.EMPTY_LIST));
        assertTrue(CollectionUtils.isEmpty((Collection<?>) null));
        assertFalse(CollectionUtils.isEmpty(Arrays.asList("1", "2")));
    }

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isNotEmpty(null)' is always 'false'" warning
    public void testIsNotEmptyCollection() {
        assertTrue(CollectionUtils.isNotEmpty(Arrays.asList("1", "2")));
        assertFalse(CollectionUtils.isNotEmpty(CollectionUtils.EMPTY_LIST));
        assertFalse(CollectionUtils.isNotEmpty((Collection<?>) null));
    }

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isEmpty(null)' is always 'true'" warning
    public void testIsEmptyArray() {
        assertTrue(CollectionUtils.isEmpty(new String[0]));
        assertTrue(CollectionUtils.isEmpty((String[]) null));
        assertFalse(CollectionUtils.isEmpty(new String[]{"1", "2"}));
    }

    @Test
    @SuppressWarnings("all")    // for the "Result of 'CollectionUtils.isNotEmpty(null)' is always 'false'" warning
    public void testIsNotEmptyArray() {
        assertTrue(CollectionUtils.isNotEmpty(new String[]{"1", "2"}));
        assertFalse(CollectionUtils.isNotEmpty(new String[0]));
        assertFalse(CollectionUtils.isNotEmpty((String[]) null));
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