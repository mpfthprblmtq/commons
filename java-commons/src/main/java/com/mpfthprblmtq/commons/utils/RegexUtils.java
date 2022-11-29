/*
 * Project: java-commons
 * File:    RegexUtils.java
 * Desc:    Utility class with methods to help with regex searches
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.utils;

// imports
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// class RegexUtils
public class RegexUtils {

    /**
     * Gets a single matched group from a string.  If there are multiple matches, will return the last match.
     * @param s the string to check
     * @param regex the regex to check with
     * @param group the name of the matched group
     * @return the matched portion of the string or null if no matches found
     */
    public static String getMatchedGroup(String s, String regex, String group) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(group);
        }
        return null;
    }

    /**
     * Gets all (one or more) matches for a regex
     * @param s the string to check
     * @param regex the regex to check with
     * @param group the name of the matched group
     * @return a list of matches for the given group
     */
    public static List<String> getAllMatchesForGroup(String s, String regex, String group) {
        List<String> matches = new ArrayList<>();

        while (true) {
            String match = getMatchedGroup(s, regex, group);
            if (StringUtils.isNotEmpty(match)) {
                matches.add(match);
                s = s.replace(match, StringUtils.EMPTY);
            } else {
                break;
            }
        }

        // reverse the list since the matcher finds them from last to first
        Collections.reverse(matches);

        return matches;
    }

    /**
     * Gets all (one or more) matches for multiple groups
     * @param s the string to check
     * @param regex the regex to check with
     * @param groups the list of names of groups to match
     * @return the matches map, keys will be the groups, values will be the list of matches for that specific group
     */
    public static Map<String, List<String>> getAllMatchesForGroups(String s, String regex, List<String> groups) {
        Map<String, List<String>> matchingGroups = new HashMap<>();
        groups.forEach(key -> matchingGroups.put(key, new ArrayList<>()));
        for (String group : groups) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                matchingGroups.get(group).add(matcher.group(group));
            }
            // remove null values
            matchingGroups.get(group).removeAll(Collections.singletonList(null));
        }
        return matchingGroups;
    }
}
