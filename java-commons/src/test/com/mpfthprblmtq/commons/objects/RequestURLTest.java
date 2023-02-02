package com.mpfthprblmtq.commons.objects;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestURLTest {

    @Test
    public void testBuild() throws MalformedURLException  {
        String expectedUrl = "https://www.google.com/1/2/3?q=query&type=search";
        String actualUrl = new RequestURL()
                .withBaseUrl("https://www.google.com/{id_1}/{id_2}/{id_3}")
                .withQueryParam("q", "query")
                .withQueryParam("type", "search")
                .withUrlParam("id_1", "1")
                .withUrlParam("id_2", "2")
                .withUrlParam("id_3", "3")
                .build();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testBuild_throwsExceptionOnBadBaseUrl() throws MalformedURLException {
        MalformedURLException exception = assertThrows(MalformedURLException.class, () -> {
            new RequestURL("https://www.google.com").withUrlParam("id_1", "1").buildUrl();
        });

        String expectedMessage = "Could not find {id_1} in given url: https://www.google.com";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testBuildUrl() throws MalformedURLException {
        URL expectedUrl = new URL("https://www.google.com/1/2/3?q=query&type=search");
        URL actualUrl = new RequestURL()
                .withBaseUrl("https://www.google.com/{id_1}/{id_2}/{id_3}")
                .withQueryParam("q", "query")
                .withQueryParam("type", "search")
                .withUrlParam("id_1", "1")
                .withUrlParam("id_2", "2")
                .withUrlParam("id_3", "3")
                .buildUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testBuildUrl_withOnlyBase() throws MalformedURLException {
        URL expectedUrl = new URL("https://www.google.com/1/2/3?q=query&type=search");
        URL actualUrl = new RequestURL("https://www.google.com/1/2/3?q=query&type=search").buildUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void testBuildUrl_throwsExceptionWhenNoBaseGiven() throws MalformedURLException {
        MalformedURLException exception = assertThrows(MalformedURLException.class, () -> {
            new RequestURL().withUrlParam("id_1", "1").buildUrl();
        });

        String expectedMessage = "No base url given!";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}