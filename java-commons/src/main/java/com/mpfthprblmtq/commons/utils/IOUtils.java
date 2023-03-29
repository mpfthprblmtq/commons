package com.mpfthprblmtq.commons.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class IOUtils {

    /**
     * Reads all file contents into a list of strings
     * @param path the path of the file
     * @return a list of all string lines in the file
     * @throws IOException if there are issues reading the file
     */
    public static List<String> readFileContentsIntoLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
    }

    /**
     * Reads all file contents into one large string
     * @param path the path of the file
     * @return a string of contents in the file
     * @throws IOException if there are issues reading the file
     */
    public static String readFileContentsIntoString(String path) throws IOException {
        // read in contents
        List<String> strings = readFileContentsIntoLines(path);

        // iterate through list and append the strings
        StringBuilder stringBuilder = new StringBuilder(StringUtils.EMPTY);
        for (String string : strings) {
            stringBuilder.append(string).append("\n");
        }

        // remove the last newline
        if (StringUtils.isNotEmpty(stringBuilder.toString())) {
            stringBuilder = new StringBuilder(stringBuilder.substring(0, stringBuilder.length() - 1));
        }

        // return the string
        return stringBuilder.toString();
    }

    /**
     * Clears the file contents
     * @param path the path of the file
     * @throws FileNotFoundException if the file is not found
     */
    public static void clearFileContents(String path) throws FileNotFoundException {
        new PrintWriter(path).close();
    }

    /**
     * Creates a new file and writes to it
     * @param path the path of the file
     * @param content the content to write
     */
    public static void createAndWriteToFile(String path, String content) throws IOException {
        // create file
        File file = new File(path);
        if (!file.createNewFile()) {
            throw new IOException("Couldn't create file: " + file.getPath());
        }

        // write
        writeToFile(path, content);
    }

    /**
     * Writes out to a file, overwriting previous contents
     * @param path the path to the file
     * @param content the content to write to the file
     * @throws IOException if there are issues writing to the file
     */
    public static void writeToFile(String path, String content) throws IOException {
        Files.writeString(Paths.get(path), content);
    }

    /**
     * Appends to a file, persisting old content and appending it to the end
     * @param path the path to the file
     * @param content the content to write to the file
     * @throws IOException if there are issues writing to the file
     */
    public static void appendToFile(String path, String content) throws IOException {
        Files.writeString(Paths.get(path), content, StandardOpenOption.APPEND);
    }
}
