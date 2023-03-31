# Java Commons

![Generic badge](https://img.shields.io/badge/version-1.0.0-brightgreen.svg)
![Language badge](https://img.shields.io/badge/Java-17-blue)
![Platform badge](https://img.shields.io/badge/Platform-OSX-lightgrey)

![Coverage (Class)](https://img.shields.io/badge/Coverage%20(Class)-100%25-green)
![Coverage (Method)](https://img.shields.io/badge/Coverage%20(Method)-91%25-green)
![Coverage (Line)](https://img.shields.io/badge/Coverage%20(Line)-90%25-green)

A library I made to clean up some projects of mine.  Built with Java 17.

**Note:** I have not tried using this library on Windows, only on OSX.  Might implement that in a future release, or 
just make sure that it works with both Windows and OSX.

[View the JavaDoc](https://www.prblmtq.com/portfolio/app/java-commons/javadoc/)

[GitHub Project (feature/bug tracking)](https://github.com/users/mpfthprblmtq/projects/1)

[Read the Wiki](https://github.com/mpfthprblmtq/commons/wiki/Java-Commons)

## High Level

This library contains all the JARs for Lombok, Jackson, and Mockito.  JUnit5.7.0 comes bundled with IntelliJ, so no need
to include that here.  This allows whatever project you're building to just pull in this library, and everything 
is included.

## Building the Library

### Automated Method

1. Complete steps 1-3 from the Manual Method (cloning the repo, opening the project in IntelliJ, and creating the build process).
2. Complete steps i-vii from step 4. (This is to get the javadoc configuration set up initially).
3. Create a new Run Configuration:
   1. Select *Edit Configurations...* in the Run Configurations dropdown.
   2. Click the + icon in the top left and select **Shell Script**.
   3. Name it something meaningful like "package" and set the *Script Path* option to the `package.sh` file in the main directory of the `java-commons` directory.
   4. In the *Before Launch* section, click the + icon and select **Build Artifact**, then select your build configuration from step 3 of the Manual Method steps.
   5. Hit **OK**
4. Run this configuration and follow the prompts the script gives you whenever you want to package the library.


### Manual Method

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


4. Building the JavaDocs:
   1. Go to *Tools -> Generate JavaDoc...*
   2. Select **Whole project** under Generate JavaDoc Scope.
   3. (Optional) Uncheck *Include test sources* under Generate JavaDoc Scope.
   4. Set the *Output directory* to something like `out/javadoc` (used in a future step).
   5. (Optional) Set a Locale (I used `en_US`, not sure what format it's expecting here).
   6. (Optional) Un-select the *Open generated documentation in browser* checkbox to disable the javadoc webpage being opened every time you generate the javadoc.
   7. Hit **OK**.
   8. Open a terminal, navigate to that directory the javadocs were created in and run the following commands:
   ```shell
   cd out/javadoc
   jar cf java-commons-X.X.X-javadoc.jar *
   ```
   **Note:** Make sure to use the correct version instead of 0.0.0


5. Building the library into a JAR file:
   1. Go to *Build -> Build Artifacts...*
   2. On the *Build Artifact* menu that opens up, you should only have one artifact to build, select **Build** from the Actions list.
   3. The built library jar file should now be in the `out/artifacts/java_commons_jar` directory.


6. Importing the source jar and javadoc jar as part of your project:
   1. In your project, go to *File -> Project Structure*.
   2. Under Project Settings, select **Libraries**.
   3. Hit the + symbol and select the source jar and the javadoc jar.  (I usually like to have a `lib/` folder in my project, so I can keep my jars centrally located)
   4. Hit OK on the Project Structure dialog.


You're done and ready to use Java-Commons!

## Contributing

I'm open to suggestions and changes, feel free to fork the repo or get in touch with me!

---

© Pat Ripley 2022-2023