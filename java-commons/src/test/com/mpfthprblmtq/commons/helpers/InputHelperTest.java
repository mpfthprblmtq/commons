package com.mpfthprblmtq.commons.helpers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class InputHelperTest {

    private InputHelper inputHelper;

    @BeforeEach
    public void beforeEach() {
        // create our inputHelper with blank print stream output, so we can ignore console output by default
        // any place where we're asserting on console output, we can just set the output streams there
        inputHelper = new InputHelper(System.in, new PrintStream(new ByteArrayOutputStream()), new PrintStream(new ByteArrayOutputStream()));
    }

    @Test
    public void testNoArgsConstructor() {
        inputHelper = new InputHelper();
        // can only assert that inputHelper.getInputStream is of type BufferedReader
        assertNotNull(inputHelper.getInputStream());
        assertSame(inputHelper.getInputStream().getClass(), BufferedReader.class);
        assertEquals(System.out, inputHelper.getOutputStream());
        assertEquals(System.err, inputHelper.getErrorStream());
    }

    @Test
    public void testGetYesNoInput_whenGivenValidInput_returnsTrue() throws IOException {
        // create an input stream with just "y" or "n"
        ByteArrayInputStream inputStreamYes = new ByteArrayInputStream("y".getBytes(StandardCharsets.UTF_8));
        ByteArrayInputStream inputStreamNo = new ByteArrayInputStream("n".getBytes(StandardCharsets.UTF_8));

        // yes
        inputHelper.setInputStream(inputStreamYes);
        assertTrue(inputHelper.getYesNoInput("query"));

        // no
        inputHelper.setInputStream(inputStreamNo);
        assertFalse(inputHelper.getYesNoInput("query"));
    }

    @Test
    public void testGetYesNoInput_whenGivenInvalidInput_thenShowsErrorString() throws IOException {
        // create input streams with custom user input, first two are invalid
        ByteArrayInputStream inputStreamUppercaseText = new ByteArrayInputStream("Yes\nno\nY".getBytes(StandardCharsets.UTF_8));
        inputHelper.setInputStream(inputStreamUppercaseText);

        // create output stream so we can assert on the output
        ByteArrayOutputStream outputStreamContent = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(outputStreamContent);
        inputHelper.setOutputStream(outputStream);

        // get the user input
        boolean input = inputHelper.getYesNoInput("query");

        // assert that we showed the invalid string
        assertTrue(outputStreamContent.toString().contains("Invalid input, need either Y or N (Yes/No)."));

        // count the number of times we asked the user, should be 3
        int numberOfOccurrences = 0;
        Pattern p = Pattern.compile("query");
        Matcher m = p.matcher(outputStreamContent.toString());
        while (m.find()) {
            numberOfOccurrences++;
        }
        assertEquals(3, numberOfOccurrences);
    }

    @Test
    public void testGetYesNoInput_whenGivenExitInput_thenExits() throws IOException {
        // create an input stream with exit user input
        ByteArrayInputStream inputStreamExit = new ByteArrayInputStream("exit".getBytes(StandardCharsets.UTF_8));
        inputHelper.setInputStream(inputStreamExit);

        ExitAssertions.assertExits(0, () -> inputHelper.getYesNoInput("query"));
    }

    @Test
    public void testGetInput_whenGivenCustomRegexVerifier_validatesInput() throws IOException {
        // create input streams with custom user input
        ByteArrayInputStream inputStreamUppercaseText = new ByteArrayInputStream("CHEESE".getBytes(StandardCharsets.UTF_8));
        ByteArrayInputStream inputStreamLowercaseText = new ByteArrayInputStream("cheese".getBytes(StandardCharsets.UTF_8));

        // uppercase
        inputHelper.setInputStream(inputStreamUppercaseText);
        assertEquals("CHEESE", inputHelper.getInput("query", "[A-Z]+", "invalid string"));

        // lowercase
        inputHelper.setInputStream(inputStreamLowercaseText);
        assertEquals("cheese", inputHelper.getInput("query", "[a-z]+", "invalid string"));
    }

    @Test
    public void testGetInput_whenGivenInvalidString_thenShowsErrorString() throws IOException {
        // create input streams with custom user input, first two are invalid
        ByteArrayInputStream inputStreamUppercaseText = new ByteArrayInputStream("CHEESE\nCheese\ncheese".getBytes(StandardCharsets.UTF_8));
        inputHelper.setInputStream(inputStreamUppercaseText);

        // create output stream so we can assert on the output
        ByteArrayOutputStream outputStreamContent = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(outputStreamContent);
        inputHelper.setOutputStream(outputStream);

        // get the user input
        String input = inputHelper.getInput("query", "[a-z]+", "invalid string");
        assertEquals("cheese", input);

        // assert that we showed the invalid string
        assertTrue(outputStreamContent.toString().contains("invalid string"));

        // count the number of times we asked the user, should be 3
        int numberOfOccurrences = 0;
        Pattern p = Pattern.compile("query");
        Matcher m = p.matcher(outputStreamContent.toString());
        while (m.find()) {
            numberOfOccurrences++;
        }
        assertEquals(3, numberOfOccurrences);
    }

    @Test
    public void testGetInput_whenGivenExitInput_thenExits() throws IOException {
        // create input streams with exit user input
        ByteArrayInputStream inputStreamExit = new ByteArrayInputStream("EXIT".getBytes(StandardCharsets.UTF_8));
        inputHelper.setInputStream(inputStreamExit);

        ExitAssertions.assertExits(0, () -> inputHelper.getInput("query", "", "invalid string"));
    }

    @Test
    public void testExit() {
        ExitAssertions.assertExits(0, () -> System.exit(0));
        ExitAssertions.assertExits(1, () -> System.exit(1));
    }
}
