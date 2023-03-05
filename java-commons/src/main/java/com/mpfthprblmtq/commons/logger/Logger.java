/*
 * Project: java-commons
 * File:    Logger.java
 * Desc:    Logger object to... log...
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package com.mpfthprblmtq.commons.logger;

// imports
import lombok.Data;
import com.mpfthprblmtq.commons.utils.DateUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

// class Logger
@Data
public class Logger {

    // streams
    static PrintStream errorStream;
    static PrintStream eventStream;
    static PrintStream console = System.out;   // store current System.out before assigning a new value

    // the path of the support directory
    private String appSupportPath;
    // setting for if we're in developer mode
    private boolean developerMode;

    // actual log files
    private File errorLog;
    private File eventLog;

    /**
     * Constructor
     * @param appSupportPath the path of the support directory
     * @param developerMode a boolean to indicate if we're in development or deployed
     */
    public Logger(String appSupportPath, boolean developerMode) {

        this.appSupportPath = appSupportPath;
        this.developerMode = developerMode;

        // create the logging directory if it doesn't already exist
        String logsDir_path = appSupportPath + "Logs/";
        File logsDir = new File(logsDir_path);
        if (!logsDir.exists()) {
            if (!logsDir.mkdirs()) {
                System.err.println("Couldn't create the logs directory!  Path: " + logsDir_path);
            }
        }

        // create the error log
        String errorLog_path = logsDir_path + "errorLog.log";
        errorLog = new File(errorLog_path);
        if (!errorLog.exists()) {
            try {
                if (!errorLog.createNewFile()) {
                    throw new IOException("IOException thrown trying to create the error log!");
                }
            } catch (IOException ex) {
                logError("Couldn't create error log!", ex);
            }
        }

        // create the event log
        String eventLog_path = logsDir_path + "eventLog.log";
        eventLog = new File(eventLog_path);
        if (!eventLog.exists()) {
            try {
                if (!eventLog.createNewFile()) {
                    throw new IOException("IOException thrown trying to create the event log!");
                }
            } catch (IOException ex) {
                logError("Couldn't create eventLog!", ex);
            }
        }

        // set the streams for each file
        try {
            errorStream = new PrintStream(new FileOutputStream(errorLog, true));
            eventStream = new PrintStream(new FileOutputStream(eventLog, true));
        } catch (FileNotFoundException ex) {
            System.err.println("Couldn't set the print streams to error log and event log!");
        }

        // by default, setting the System.out to the errorStream just in case I missed catches
        if (developerMode) {
            setSystemOutToConsole();
            setSystemErrToConsole();
        } else {
            setSystemOutToEventLog();
            setSystemErrToErrorLog();
        }
    }

    /**
     * Sets the System.err stream back to the console for debugging in the IDE
     */
    public void setSystemOutToConsole() {
        System.setOut(console);
    }

    /**
     * Sets the System.out stream to the eventLog
     */
    public void setSystemOutToEventLog() {
        System.setOut(eventStream);
    }

    /**
     * Sets the System.err stream to the console
     */
    public void setSystemErrToConsole() {
        System.setOut(console);
    }

    /**
     * Sets the System.err stream to the console
     */
    public void setSystemErrToErrorLog() {
        System.setErr(errorStream);
    }

    /**
     * Just straight up disables logging all together
     */
    public void disableLogging() {
        System.setErr(null);
        System.setOut(null);
    }

    /**
     * Method that actually does the logging for errors
     * @param errorString the localized string
     * @param ex the exception that was thrown
     */
    @SuppressWarnings("Duplicates")
    public void logError(String errorString, Exception ex) {

        // format today's date
        String dateStr = "[" + DateUtils.formatDetailedDateTime(new Date()) + "]";

        // get calling context
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
        String lineNumber = String.valueOf(Thread.currentThread().getStackTrace()[2].getLineNumber());

        // output the statement
        System.err.printf("%-21s %s.%s:%s - %s", dateStr, callingClass, callingMethod, lineNumber, errorString + StringUtils.NEW_LINE);
        System.err.printf("%-21s %s", "", "Exception details:  " + ex.toString() + StringUtils.NEW_LINE);
    }

    /**
     * Method that actually does the logging for errors
     * @param errorString the localized string
     */
    @SuppressWarnings("Duplicates")
    public void logError(String errorString) {

        // format today's date
        String dateStr = "[" + DateUtils.formatDetailedDateTime(new Date()) + "]";

        // get calling context
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();
        String callingMethod = Thread.currentThread().getStackTrace()[2].getMethodName();
        String lineNumber = String.valueOf(Thread.currentThread().getStackTrace()[2].getLineNumber());

        // output the statement
        System.err.printf("%-21s %s.%s:%s - %s", dateStr, callingClass, callingMethod, lineNumber, errorString + StringUtils.NEW_LINE);
    }

    /**
     * Method that actually does the logging for events
     * @param eventString the localized string
     */
    public void logEvent(String eventString) {

        // format today's date
        String dateStr = "[" + DateUtils.formatDetailedDateTime(new Date()) + "]";

        // get calling context
        String callingClass = Thread.currentThread().getStackTrace()[2].getClassName();

        // output the statement
        System.out.printf("%-21s %s - %s\n", dateStr, callingClass, eventString);

        // put it back to errorStream by default to pick up any missed exceptions
        System.setOut(errorStream);
    }
}
