/*
 * Project: java-commons
 * File:    FileUtils.java
 * Desc:    Utility class with methods to help with file processing
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package utils;

// imports
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// class FileUtils
public class FileUtils {

    /**
     * Helper Function that lists and stores all files in a directory and subdirectories
     * @param directory, the directory to list files from
     * @param files, the arrayList to store the files in
     */
    public static void listFiles(File directory, List<File> files) {
        // get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listFiles(file, files);     // this file is a directory, recursively call itself
            }
        }
    }

    /**
     * Helper function that lists and stores all files in a directory and subdirectories
     * @param directory, the directory to list files from
     * @return a list of all files
     */
    public static List<File> listFiles(File directory) {
        List<File> files = new ArrayList<>();
        listFiles(directory, files);
        return files;
    }

    /**
     * Cleans a string if it's a file on OSX systems
     * @param s, the string to clean (replace : with /)
     * @return a cleaned string
     */
    public static String cleanFilenameForOSX(String s) {
        return s.replaceAll(":", "/");
    }

    /**
     * Deletes a folder and all of its contents recursively
     * @param folder the folder to delete
     * @return a boolean result of the delete
     */
    public static boolean deleteFolder(File folder) {
        boolean result;
        File[] files = folder.listFiles();
        if (files != null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    result = f.delete();
                    if (!result) {
                        return false;
                    }
                }
            }
        }
        result = folder.delete();
        return result;
    }


    /**
     * Opens a file
     * @param file, the file to open
     * @throws IOException if there are issues opening the file
     */
    public static void openFile(File file) throws IOException, Exception {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                desktop.open(file);
            }
        } else {
            throw new Exception("Desktop is not supported!  Tried opening: " + file.getPath());
        }
    }

    /**
     * Opens the containing folder for the file
     * @param file the file to open
     * @throws IOException if the file isn't found
     */
    public static void showInFolder(File file) throws IOException {
        String path = file.getPath().replace(file.getName(), StringUtils.EMPTY);
        if (Desktop.isDesktopSupported()) {
            if (file.exists()) {
                Desktop.getDesktop().open(new File(path));
            }
        }
    }

    /**
     * Creates a JFileChooser, configures it, and launches it
     * Returns a single index array if there's only one file returned
     *
     * @param title,                   the title of the window
     * @param approveButtonText,       the text to show on the approve button
     * @param selectionMode,           the mode for selecting files
     * @param multipleSelection,       a boolean for allowing multiple file selection in the window
     * @param openAt,                  an optional parameter to open the JFileChooser at a certain location
     * @param fileNameExtensionFilter, an optional parameter to set the filter to look at files on
     * @return a file array of the selected file(s)
     */
    @SuppressWarnings("all") // for the always true/false assertion
    public static File[] launchJFileChooser(
            String title,
            String approveButtonText,
            int selectionMode,
            boolean multipleSelection,
            File openAt,
            FileNameExtensionFilter fileNameExtensionFilter) {

        // create it
        JFileChooser jfc = new JFileChooser();

        // configure it
        jfc.setCurrentDirectory(openAt == null ? new File(System.getProperty("user.home")) : openAt);

        // some normal fields
        jfc.setDialogTitle(title);
        jfc.setMultiSelectionEnabled(multipleSelection);
        jfc.setFileSelectionMode(selectionMode);

        // filter the files based on the extensions
        if (fileNameExtensionFilter != null) {
            jfc.setFileFilter(fileNameExtensionFilter);
        }

        // launch it
        int returnVal = jfc.showDialog(null, approveButtonText);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (multipleSelection) {
                return jfc.getSelectedFiles();
            } else {
                return new File[]{jfc.getSelectedFile()};
            }
        } else {
            return null;
        }
    }

    /**
     * Gets the common base starting point for a list of files
     * @param files, the list of files to search
     * @return a File starting point
     */
    public static File getStartingPoint(List<File> files) {
        String commonPath = "";
        String[][] folders = new String[files.size()][];
        for (int i = 0; i < files.size(); i++) {
            folders[i] = files.get(i).getPath().split("/");
        }
        for (int j = 0; j < folders[0].length; j++) {
            String thisFolder = folders[0][j];  // grab the next folder name in the first path
            boolean allMatched = true;  // assume all have matched in case there are no more paths
            // look at the other paths
            for (int i = 1; i < folders.length && allMatched; i++) {
                // if there is no folder here
                if (folders[i].length < j) {
                    allMatched = false; // no match
                    break;              // stop looking because we've gone as far as we can
                }
                // otherwise, check if it matched
                allMatched = folders[i][j].equals(thisFolder);
            }
            // if they all matched this folder name
            if (allMatched) {
                commonPath += thisFolder + "/"; // add it to the answer
            } else {
                // stop looking
                break;
            }
        }
        return new File(commonPath);
    }
}
