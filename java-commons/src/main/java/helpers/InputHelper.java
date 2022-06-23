/*
 * Project: java-commons
 * File:    InputHelper.java
 * Desc:    Helper class with methods used to get user input from the command line.
 *
 * Copyright © Pat Ripley / PRBLMTQ 2022
 */

// package
package helpers;

// imports
import lombok.Data;

import java.io.*;
import java.util.Locale;

// class InputHelper
@Data
public class InputHelper {

    // input and output streams
    private BufferedReader inputStream;
    private PrintStream outputStream;
    private PrintStream errorStream;

    // basic constructor
    public InputHelper() {
        this.inputStream = new BufferedReader(new InputStreamReader(System.in));
        this.outputStream = System.out;
        this.errorStream = System.err;
    }

    // custom constructor
    public InputHelper(InputStream in, PrintStream out, PrintStream err) {
        this.inputStream = new BufferedReader(new InputStreamReader(in));
        this.outputStream = out;
        this.errorStream = err;
    }

    // custom setter for the inputStream
    public void setInputStream(InputStream in) {
        this.inputStream = new BufferedReader(new InputStreamReader(in));
    }

    /**
     * Gets user input from the command line
     * @param query the query to ask the user, basis for the input
     * @param regexVerifier a regex string that determines "valid" user input.  For example, the regex string "[YyNn]{1}" would only allow Y and N characters, uppercase or lowercase
     * @param invalidInputString the string to show to the user when they get input wrong
     * @return some valid user input
     * @throws IOException if there's a problem with the input stream
     */
    public String getInput(String query, String regexVerifier, String invalidInputString) throws IOException {
        boolean validInput = false;
        String input = "";

        // loop while the input isn't valid
        while (!validInput) {
            // ask the question
            outputStream.println(query);
            outputStream.print("\t: ");

            // get the answer
            input = inputStream.readLine();
            if (input.toLowerCase(Locale.ROOT).equals("exit")) {
                System.out.println();
                System.exit(0);
            }
            if (input.matches(regexVerifier)) {
                validInput = true;
            } else {
                outputStream.println(invalidInputString);
                outputStream.println();
            }
        }
        return input;
    }
}
