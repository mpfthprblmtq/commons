/*
 * Project: java-commons
 * File:    WebUtils.java
 * Desc:    Utility class with methods to help with web pages
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.utils;

// imports
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

// class WebUtils
public class WebUtils {

    /**
     * Opens a webpage with the specified url
     *
     * @param url, the url to open
     * @throws URISyntaxException if the URI is malformed
     * @throws IOException if there's issues opening the page
     * @throws Exception if Desktop is not supported
     */
    public static void openPage(String url) throws URISyntaxException, IOException, Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(new URI(url));
        } else {
            throw new Exception("Desktop is not supported!  Tried opening: " + url);
        }
    }

    /**
     * Makes a post request with the given parameters
     * @param url a URL object to call
     * @param doOutput a Boolean value used to determine if we're getting output from the request (defaults to false)
     * @param requestProperties the headers for the request
     * @param body a string representation of the request body
     * @return a JSON String of the response
     * @throws IOException if there was a problem calling the service
     */
    public static String post(URL url, Boolean doOutput, Map<String, String> requestProperties, String body) throws IOException {
        // set up the request
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(doOutput != null ? doOutput : false);
        for (String property : requestProperties.keySet()) {
            http.setRequestProperty(property, requestProperties.get(property));
        }

        // encode request body
        byte[] out = body.getBytes(StandardCharsets.UTF_8);

        // make the request
        OutputStream stream = http.getOutputStream();
        stream.write(out);

        // parse the response
        BufferedReader lines = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String currentLine = lines.readLine();
        StringBuilder response = new StringBuilder();
        while (currentLine != null) {
            response.append(currentLine).append("\n");
            currentLine = lines.readLine();
        }

        // clean up
        http.disconnect();

        return response.toString();
    }

    /**
     * Makes a get request with the given parameters
     * @param url a URL object to call
     * @param requestProperties the headers for the request
     * @return a JSON String of the response
     * @throws IOException if there was a problem calling the service
     */
    public static String get(URL url, Map<String, String> requestProperties) throws IOException {
        // set up the request
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        for (String property : requestProperties.keySet()) {
            http.setRequestProperty(property, requestProperties.get(property));
        }

        // make the call and store the response in a string
        BufferedReader Lines = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String currentLine = Lines.readLine();
        StringBuilder response = new StringBuilder();
        while (currentLine != null) {
            response.append(currentLine).append(StringUtils.NEW_LINE);
            currentLine = Lines.readLine();
        }

        // clean up
        http.disconnect();

        return response.toString();
    }
}
