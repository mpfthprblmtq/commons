package com.mpfthprblmtq.commons.logger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import testingUtils.TestingUtils;
import com.mpfthprblmtq.commons.utils.FileUtils;
import com.mpfthprblmtq.commons.utils.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

class LoggerTest {

    static File testFolder = new File("src/test/temp/");
    static Logger logger;

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    static void setUp() {
        logger = new Logger("src/test/temp/", true);
    }

    @AfterAll
    static void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
        FileUtils.deleteFolder(testFolder);
    }

    @Test
    public void testLogError() {
        System.setErr(new PrintStream(errContent));
        String logString = "This is a test error";
        logger.logError(logString);

        // assert that the context and log message was logged, I'm not too worried about the date and time being right
        assertTrue(errContent.toString().contains("LoggerTest"));
        assertTrue(errContent.toString().endsWith(logString + StringUtils.NEW_LINE));
    }

    @Test
    public void testLogErrorWithException() {
        System.setErr(new PrintStream(errContent));
        String logString = "This is a test error";
        String exceptionString = "This is an exception string";
        Exception e = new Exception(exceptionString);
        logger.logError(logString, e);

        // assert that the context, log message, and exception was logged, I'm not too worried about the date and time being right
        assertTrue(errContent.toString().contains("com.mpfthprblmtq.commons.logger.LoggerTest.testLogErrorWithException:"));
        assertTrue(errContent.toString().contains(logString));
        assertTrue(errContent.toString().contains("Exception details:  java.lang.Exception"));
        assertTrue(errContent.toString().contains(exceptionString));
    }

    @Test
    public void testLogEvent() {
        System.setOut(new PrintStream(outContent));
        String logString = "This is a test event";
        logger.logEvent(logString);

        // assert that the context and log message was logged, I'm not too worried about the date and time being right
        assertTrue(outContent.toString().contains("LoggerTest"));
        assertTrue(outContent.toString().endsWith(logString + StringUtils.NEW_LINE));
    }

    @Test
    public void testLogError_logsToFile() {
        logger.setDeveloperMode(false);
        logger.setSystemErrToErrorLog();
        String logString = "This is a test error";
        logger.logError(logString);
        String filePath = testFolder.getPath() + "/Logs/errorLog.log";

        List<String> lines = TestingUtils.readFileContents(filePath);
        assert lines != null;
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("com.mpfthprblmtq.commons.logger.LoggerTest.testLogError_logsToFile:"));  // context
        assertTrue(lines.get(0).endsWith(logString));       // message

        // cleanup
        TestingUtils.clearFileContents(filePath);
    }

    @Test
    public void testLogErrorWithException_logsToFile() {
        logger.setDeveloperMode(false);
        logger.setSystemErrToErrorLog();
        String logString = "This is a test error";
        String exceptionString = "This is an exception string";
        Exception e = new Exception(exceptionString);
        logger.logError(logString, e);
        String filePath = testFolder.getPath() + "/Logs/errorLog.log";

        // assert that the context, log message, and exception was logged, I'm not too worried about the date and time being right
        List<String> lines = TestingUtils.readFileContents(filePath);
        assert lines != null;
        assertEquals(2, lines.size());
        assertTrue(lines.get(0).contains("com.mpfthprblmtq.commons.logger.LoggerTest.testLogErrorWithException_logsToFile:"));
        assertTrue(lines.get(0).contains(logString));
        assertTrue(lines.get(1).contains("Exception details:  java.lang.Exception"));
        assertTrue(lines.get(1).contains(exceptionString));

        // cleanup
        TestingUtils.clearFileContents(filePath);
    }

    @Test
    public void testLogEvent_logsToFile() {
        logger.setDeveloperMode(false);
        logger.setSystemOutToEventLog();
        String logString = "This is a test event";
        logger.logEvent(logString);
        String filePath = testFolder.getPath() + "/Logs/eventLog.log";

        List<String> lines = TestingUtils.readFileContents(filePath);
        assert lines != null;
        assertEquals(1, lines.size());
        assertTrue(lines.get(0).contains("LoggerTest"));    // context
        assertTrue(lines.get(0).endsWith(logString));       // message

        // cleanup
        TestingUtils.clearFileContents(filePath);
    }

    @Test
    public void testDisableLogging() {
        logger.disableLogging();
        assertNull(System.out);
        assertNull(System.err);
    }

}