# Java Commons

![Generic badge](https://img.shields.io/badge/version-0.0.1-brightgreen.svg)
![Language badge](https://img.shields.io/badge/Java-8-blue)
![Platform badge](https://img.shields.io/badge/Platform-OSX-lightgrey)

A library I made to clean up some projects of mine.  Built with Java 8.

**Note:** I have not tried using this library on Windows, only on OSX.  Might implement that in a future release, or just make sure that it works with both Windows and OSX.

[View the JavaDoc](https://www.prblmtq.com/portfolio/app/java-commons/javadoc/)

[Github Project (feature/bug tracking)](https://github.com/users/mpfthprblmtq/projects/1)

---

## Contents

Here's what's under the hood:

### Helpers
<details>
  <summary markdown="span">Input Helper</summary>


A command line-based helper that takes user input and validates it based on a regex parameter.

**Example Usage:**

```java
public class Main {
    public static void main(String[] args) {
        
        // get the user's input
        String input = InputHelper.getInput(
            "Would you like to continue? [Y/N]",
            "[YyNn]{1}",
            "Invalid input, try again!"
        );
        
        // process with result
        if (input.equals("Y") || input.equals("y")) {
            doSomethingWithYes();
        } else {
            doSomethingWithNo();
        }
    }
}

```
</details>

### Loggers
<details>
  <summary markdown="span">Logger</summary>


A configurable logger that logs things to the console or to a file.  

The first parameter in the constructor is a String path where it creates a "support" directory, and within that directory, a `Logs` folder where it will create an `eventLog.log` file and an `errorLog.log` file.  
The second parameter is a `developerMode` boolean that tells the logger if we should use the files for logging or just stick to the console (usually your IDE's console).

**Example Usage:**

```java
public class Main {
    public static void main(String[] args) {
        // initialize the logger
        Logger logger = new Logger("path/to/support/directory", true);
        
        // use the logger
        try {
            doSomething();
            logger.logEvent("Something successfully done!");
        } catch (Exception e) {
            logger.logError("Something went wrong!");
            // or
            logger.logError("Something went wrong, but with an exception!", e);
        }
    }
}
```

The code above will result in log messages that look like this:
```log
[01/02/03 04:05] SomeClassName.SomeMethod - Something successfully done!
[01/02/03 04:05] SomeClassName.SomeMethod:99 - Something went wrong!
[01/02/03 04:05] SomeClassName.SomeMethod:99 - Something went wrong, but with an exception!
                 Exception Details:  Exception message
```

</details>

### Utilities
<details>
  <summary markdown="span">DateUtils</summary>


A utility class that handles the parsing of dates from strings and vice versa.  Currently, the only date formats I have in there are a simple date (MM-dd-yyyy) and a more complicated datetime (MM/dd/yyyy HH:mm:ss) mainly for logging.  But you can pass it your own SimpleDateFormat object and let the utility parse things for you.

**Example Usage:**

```java
import utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // get a Date object from a MM-dd-yyyy formatted String
        Date simpleDateFromString = DateUtils.getSimpleDate("03-23-1994");
        // get a Date object from a MM/dd/yyyy HH:mm:ss formatted String
        Date detailDateFromString = DateUtils.getDetailedDateTime("03/23/1994 01:02:03");
        // get a Date object from a custom formatted String
        Date customDateFromString = DateUtils.getCustomDateTime("03 23 1994", new SimpleDateFormat("MM dd yyyy"));

        // get a simple formatted String from a Date object
        String simpleDateString = DateUtils.formatSimpleDate(simpleDateFromString);
        // get a detailed formatted String from a Date object
        String detailedDateString = DateUtils.formatDetailedDateTime(detailDateFromString);
        // get a custom formatted String from a Date object
        String customDateString = DateUtils.formatCustomDateTime(customDateFromString, new SimpleDateFormat("MM dd yyyy"));
        
        // check if a date is the same as today
        boolean isSame = DateUtils.isDateSameAsToday(new Date());           // will return true since new Date() returns the current day
        boolean isSame = DateUtils.isDateSameAsToday(simpleDateFromString); // will return false since the date being handed to the method is 03/23/1994
    }
}
```
</details>  

<details>
  <summary markdown="span">FileUtils</summary>


A utility class that has some file based methods like listing all files in a directory, cleaning filenames, deleting folders, opening files, a "Show in Folder" function, a utility function to find the starting point in a group of files, and a fully customizable Swing JFileChooser.

**Example Usage:**

```java
import utils.FileUtils;

public class Main {
    public static void main(String[] args) {
        // gets all the files in a directory
        List<File> filesInDirectory = FileUtils.listFiles(new File("path/to/directory"));
        
        FileUtils.openFile(file);   // will open file with whatever default application is configured for your system
        FileUtils.showInFolder(file);   // will open Finder/File Explorer in the directory the file is in
    }
}
```

</details>

<details>
  <summary markdown="span">StringUtils</summary>


A utility class that has some basic null-safe String interpolation functions, like isEmpty, isNotEmpty, some number formatting methods, and a function to check if all elements in an array/list are the same.

**Example Usage:**

```java
import utils.StringUtils;

public class Main {
    public static void main(String[] args) {
        boolean result1 = StringUtils.isEmpty("");  // will return true
        boolean result2 = StringUtils.isEmpty("test");  // will return false
        String result3 = StringUtils.validateString(null);   // will return in a blank string
        String result4 = StringUtils.formatNumber(123456789);    // will return "123,456,789"
    }
}
```
</details>

<details>
  <summary markdown="span">WebUtils</summary>


A utility class for opening web pages.  (That's currently all it has)

**Example Usage:**

```java
import utils.WebUtils;

public class Main {
    public static void main(String[] args) {
        String url = "https://github.com/mpfthprblmtq/commons/tree/main/java-commons";
        WebUtils.openPage(url); // will open the url in the system's default browser
    }
}
```
</details>

### Wrappers

<details>
  <summary markdown="span">JsonWrapper</summary>


A jackson utility for an ObjectMapper, with a builder pattern.  (Really just eliminates the Jackson import)

**Example Usage:**

```java
import wrappers.JsonWrapper;

public class Main {
    public static void main(String[] args) {
        // default wrapper with setter method
        JsonWrapper wrapper = new JsonWrapper();
        wrapper.setProperty(JsonWrapper.ALLOW_COMMENTS, true);
        
        // wrapper with builder pattern
        JsonWrapper builderWrapper = new JsonWrapper()
                .withProperty(JsonWrapper.ALLOW_COMMENTS, true)
                .withProperty(JsonWrapper.ALLOW_SINGLE_QUOTES, true)
                .withProperty(JsonWrapper.IGNORE_UNDEFINED, true);
    }
}
```
</details>

---

## Building the Library

1. Clone the entire repository down to your machine (the commons repository, not just the java-commons directory):
    ```shell
    git clone https://github.com/mpfthprblmtq/commons.git
    ```


2. Opening the `java-commons` project in IntelliJ:
   1. Select *New -> Project from Existing Sources*.  Go to the repository you just cloned and select the `java-commons` folder.
   2. On the Import Project dialog, leave “Create project from existing sources” selected and hit **Next**.
   3. Leave the project name as “java-commons” and all the other settings the same and hit **Next**.
   4. Leave the `src/main/java` and the `src/test` folders selected for the Source files and hit **Next**.
   5. Leave the detected libraries selected (should be Jackson and Lombok for now) and hit **Next**.
   6. Leave the module structure checkboxes checked and hit **Next**.
   7. Select Java 1.8 and hit **Next**.
   8. Hit **Finish**.  


3. Creating the build process:
   1. Go to *File -> Project Structure* (or use the shortcut ⌘ ; for OSX).
   2. Under *Project Settings*, select **Artifacts**.
   3. Select the + icon.
   4. Hover over **JAR** then select **From modules with dependencies...**
   5. On the *Create JAR from Modules* dialog, leave everything the way it is and hit **OK**.
   6. Hit **OK** on the *Project Structure* dialog.


4. Building the library into a JAR file:
   1. Go to *Build -> Build Artifacts...*
   2. On the *Build Artifact* menu that opens up, you should only have one artifact to build, select **Build** from the Actions list.
   3. The built library jar file should now be in the `out/artifacts/java_commons_jar` directory.


5. Building the JavaDocs:
   1. Go to *Tools -> Generate JavaDoc...*
   2. Select **Whole project** under Generate JavaDoc Scope.
   3. (Optional) Uncheck *Include test sources* under Generate JavaDoc Scope.
   4. Set the *Output directory* to something like `out/javadoc` (used in a future step).
   5. (Optional) Set a Locale (I used `en_US`, not sure what format it's expecting here).
   6. Hit **OK**.
   7. Open a terminal, navigate to that directory the javadocs were created in and run the following commands:
   ```shell
   cd out/javadoc
   jar cf java-commons-0.0.0-javadoc.jar *
   ```
   **Note:** Make sure to use the correct version instead of 0.0.0


6. Importing the source jar and javadoc jar as part of your project:
   1. In your project, go to *File -> Project Structure*.
   2. Under Project Settings, select **Libraries**.
   3. Hit the + symbol and select the source jar and the javadoc jar.  (I usually like to have a `lib/` folder in my project so I can keep my jars centrally located)
   4. Hit OK on the Project Structure dialog.


You're done and ready to use Java-Commons!

---

## Contributing

I'm open to suggestions and changes, feel free to fork the repo or get in touch with me!
