package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegexUtilsTest {

    @Test
    public void testGetMatchingGroup() {
        String actual = RegexUtils.getMatchedGroup(
                "2022 asdf 7890 what 38", ".*(?<year>\\d{4}).*", "year");
        assertEquals("7890", actual);
    }

    @Test
    public void testGetAllMatchesForGroup() {
        List<String> actual = RegexUtils.getAllMatchesForGroup(
                " 2022 asdf 7890 what 38", ".*(?<year>\\d{4}).*", "year");
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals("2022", actual.get(0));
        assertEquals("7890", actual.get(1));
    }

    @Test
    public void testGetAllMatchingGroups() {
        Map<String, List<String>> actual = RegexUtils.getAllMatchesForGroups(
                " 2022 asdf 7890 what 38", "((?<year>\\d{4})|(?<word>[A-Za-z]+))", Arrays.asList("year", "word"));
        assertNotNull(actual);
        assertEquals(2, actual.size());
        assertEquals(2, actual.get("year").size());
        assertEquals(2, actual.get("word").size());
        assertEquals("2022", actual.get("year").get(0));
        assertEquals("7890", actual.get("year").get(1));
        assertEquals("asdf", actual.get("word").get(0));
        assertEquals("what", actual.get("word").get(1));
    }

    @Test
    public void testCountOccurrences_returnsZeroForStringWithNoMatches() {
        String toCheck = "This is a string where we count the number of occurrences.";
        String checkFor = "\\{}";
        assertEquals(0, RegexUtils.countOccurrencesOfMatch(toCheck, checkFor));
    }

    @Test
    public void testCountOccurrences_returnsTwoForStringWithTwoMatches() {
        String toCheck = "This is a {} where we count the number of {}.";
        String checkFor = "\\{}";
        assertEquals(2, RegexUtils.countOccurrencesOfMatch(toCheck, checkFor));
    }

}