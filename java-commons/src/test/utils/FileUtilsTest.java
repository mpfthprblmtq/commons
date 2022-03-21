package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtilsTest {

    @Test
    public void testGetStartingPoint() {
        List<File> files = new ArrayList<>(Arrays.asList(
                new File("test1/test2/library/directory1/directory2"),
                new File("test1/test2/library/directory3/directory4"),
                new File("test1/test2/library/directory3")));

        String expected = "test1/test2/library";
        Assertions.assertEquals(expected, FileUtils.getStartingPoint(files).getPath());
    }
}
