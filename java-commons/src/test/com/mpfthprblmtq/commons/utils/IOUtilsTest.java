package com.mpfthprblmtq.commons.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilsTest {

    static File testFolder = new File("src/test/temp");
    static final String testFilePath = testFolder.getPath() + "/testFile.txt";

    @BeforeEach
    @SuppressWarnings("all") // for ignoring the result of the file stuff
    void setUp() {
        testFolder.mkdirs();
    }

    @AfterEach
    @SuppressWarnings("all") // for ignoring the result of the file stuff
    void tearDown() {
        assertTrue(FileUtils.deleteFolder(testFolder));
        assertFalse(testFolder.exists());
    }

    @Test
    public void testReadFileContentsIntoLines_readsFile() throws IOException {
        writeTestFile();
        List<String> lines = IOUtils.readFileContentsIntoLines(testFilePath);
        assertNotNull(lines);
        assertEquals(3, lines.size());
        assertEquals("content1", lines.get(0));
    }

    @Test
    public void testReadFileContentsIntoString_readsFileAndBuildsString() throws IOException {
        writeTestFile();
        String contents = IOUtils.readFileContentsIntoString(testFilePath);
        assertTrue(StringUtils.isNotEmpty(contents));
        assertFalse(contents.endsWith("\n"));
    }

    @Test
    public void testClearFileContents_clearsFileContents() throws IOException {
        writeTestFile();
        IOUtils.clearFileContents(testFilePath);
        String contents = IOUtils.readFileContentsIntoString(testFilePath);
        assertTrue(StringUtils.isEmpty(contents));
    }

    @Test
    public void testAppendToFile_appendsToFile() throws IOException {
        writeTestFile();
        IOUtils.appendToFile(testFilePath, "\ncontent4");
        String contents = IOUtils.readFileContentsIntoString(testFilePath);
        assertTrue(contents.endsWith("content4"));
    }

    @Test
    public void testCreateAndWriteToFile_whenFileAlreadyExists_thenThrowsIOException() throws IOException {
        writeTestFile();
        IOException expectedThrown = assertThrows(
                IOException.class,
                this::writeTestFile,
                "Expected IOException, but not thrown.");

        assertTrue(expectedThrown.getMessage().startsWith("Couldn't create file:"));
        assertTrue(expectedThrown.getMessage().endsWith(testFilePath));
    }

    private void writeTestFile() throws IOException {
        IOUtils.createAndWriteToFile(testFilePath, "content1\ncontent2\ncontent3");
    }
}