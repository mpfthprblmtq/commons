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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
}
