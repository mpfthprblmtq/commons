package testingUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TestingUtils {

    /**
     * Reads all file contents into a list of strings
     * @param path the path of the file
     * @return a list of all string lines in the file
     */
    public static List<String> readFileContents (String path) {
        try {
            return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Couldn't read file: " + path);
            return null;
        }
    }

    /**
     * Clears the file contents
     * @param path the path of the file
     */
    public static void clearFileContents(String path) {
        try {
            new PrintWriter(path).close();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't clear file contents: " + path);
        }
    }
}
