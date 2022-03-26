package utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtilsTest {

    static File testFolder = new File("src/test/temp");
    static List<File> testFiles = new ArrayList<>(Arrays.asList(
            new File(testFolder.getPath() + "/file1.txt"),
            new File(testFolder.getPath() + "/file2.txt"),
            new File(testFolder.getPath() + "/file3.txt"),
            new File(testFolder.getPath() + "/folder1/file4.txt"),
            new File(testFolder.getPath() + "/folder2/file5.txt"),
            new File(testFolder.getPath() + "/folder2/file6.txt"),
            new File(testFolder.getPath() + "/folder2/folder3/file7.txt"),
            new File(testFolder.getPath() + "/folder2/folder3/file8.txt")
    ));

    @BeforeAll
    @SuppressWarnings("all") // for ignoring the result of the file stuff
    static void setUp() {
        testFolder.mkdirs();
        new File(testFolder.getPath() + "/folder1").mkdirs();
        new File(testFolder.getPath() + "/folder2").mkdirs();
        new File(testFolder.getPath() + "/folder2/folder3").mkdirs();
        try {
            for (File file : testFiles) {
                file.createNewFile();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    @AfterAll
    @SuppressWarnings("all") // for ignoring the result of the file stuff
    static void tearDown() {
        FileUtils.deleteFolder(testFolder);
        Assertions.assertFalse(testFolder.exists());
    }

    @Test
    public void testListFiles() {
        List<File> files = new ArrayList<>();
        FileUtils.listFiles(testFolder, files);

        Assertions.assertTrue(files.containsAll(testFiles));
        Assertions.assertTrue(testFiles.containsAll(files));
    }

    @Test
    public void testListFilesWithNoListOverload() {
        List<File> files = FileUtils.listFiles(testFolder);

        Assertions.assertTrue(files.containsAll(testFiles));
        Assertions.assertTrue(testFiles.containsAll(files));
    }

    @Test
    public void testDeleteFolder() {
        // already tested in the tearDown() method above
    }

    @Test
    public void testGetStartingPoint() {
        List<File> files = new ArrayList<>(Arrays.asList(
                new File("test1/test2/library/directory1/directory2"),
                new File("test1/test2/library/directory3/directory4"),
                new File("test1/test2/library/directory3")));

        String expected = "test1/test2/library";
        Assertions.assertEquals(expected, FileUtils.getStartingPoint(files).getPath());
    }

    @Test
    public void testOpenFile() {
        // TODO is this even possible to test?
    }

    @Test
    public void testShowInFolder() {
        // TODO is this even possible to test?
    }
}
