package com.mpfthprblmtq.commons.translation.utils;

import com.mpfthprblmtq.commons.translation.model.exception.InvalidLocaleException;
import com.mpfthprblmtq.commons.utils.RegexUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;

import java.util.Locale;

import static com.mpfthprblmtq.commons.translation.TranslationConstants.*;

public class LocaleUtils {

    /**
     * Attempts to grab the locale from the file path.
     * Takes all sorts of locale string types (en_US, en-US, eng-USA, etc.) and creates a locale from the ISO2 language
     * and ISO2 country, which Java likes better than the ISO3 variant for some reason.
     * @param translationFile the string to parse through
     * @return a Locale, or null if it couldn't grab the locale from the file path
     * @throws InvalidLocaleException if the locale given is invalid
     */
    public static Locale getLocaleFromFilePath(String translationFile) throws InvalidLocaleException {
        // try to infer locale from filepath
        if (translationFile.matches(EXACT_LOCALE_FILEPATH_REGEX)) {
            // we have an exact match on the locale (xx-XX) format
            String localeString = RegexUtils.getMatchedGroup(translationFile, EXACT_LOCALE_FILEPATH_REGEX, "locale");
            localeString = StringUtils.isEmpty(localeString) ? StringUtils.EMPTY : localeString;
            Locale locale = Locale.forLanguageTag(localeString);
            // verify that locale is a valid locale
            if (AVAILABLE_LOCALES.contains(locale)) {
                return locale;
            }
            // invalid locale
            throw new InvalidLocaleException("Invalid Locale given: " + localeString);

        } else if (translationFile.matches(LOCALE_FILEPATH_REGEX)) {
            // grab the language and country
            String language = RegexUtils.getMatchedGroup(
                    translationFile, LOCALE_FILEPATH_REGEX, "language");
            String country = RegexUtils.getMatchedGroup(
                    translationFile, LOCALE_FILEPATH_REGEX, "country");

            // we know that language and country are not empty because they're matched in the regex
            language = StringUtils.isEmpty(language) ? StringUtils.EMPTY : language;
            country = StringUtils.isEmpty(country) ? StringUtils.EMPTY : country;

            // try and create the locale
            return getStandardizedLocale(language, country);
        }
        // couldn't get the locale from the file path
        return null;
    }

    /**
     * Helper method to try and get the ISO2 locale from ISO3 language or country. Uses the existing country ISO locales
     * provided in java.util.Locale as well as a custom ISO3 -> ISO2 language map.
     * @param language the language to parse
     * @param country the country to parse
     * @return a valid locale, or null if the locale is invalid
     * @throws InvalidLocaleException if the language/country combination is invalid
     */
    public static Locale getStandardizedLocale(String language, String country) throws InvalidLocaleException {

        // ISO2 data to create the locale with
        String ISO2Language = StringUtils.EMPTY;
        String ISO2Country = StringUtils.EMPTY;

        // make sure we have ISO2 language
        if (language.length() == 3) {
            // check to see if it's in the known ISO3 -> ISO2 language map
            if (ISO2_LANGUAGES_MAP.containsKey(language)) {
                ISO2Language = ISO2_LANGUAGES_MAP.get(language);
            }
        } else {
            ISO2Language = language;
        }

        // make sure we have ISO2 country
        if (country.length() == 3) {
            // get the ISO2 country by checking the Locale.getIsoCountries() list
            for (Locale locale : ISO2_COUNTRIES_LIST) {
                if (locale.getISO3Country().equals(country)) {
                    ISO2Country = locale.getCountry();
                    break;
                }
            }
        } else {
            ISO2Country = country;
        }

        // try to create a locale, return if valid, else throw an exception
        Locale locale = new Locale(ISO2Language, ISO2Country);
        if (AVAILABLE_LOCALES.contains(locale)) {
            return locale;
        }
        throw new InvalidLocaleException("Invalid Locale given: " + locale);
    }
}
