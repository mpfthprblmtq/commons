echo
echo "                                                   © PRBLMTQ - Pat Ripley"
echo "  (_)"
echo "   _  __ ___   ____ _            ___ ___  _ __ ___  _ __ ___   ___  _ __  ___"
echo "  | |/ _\` \ \ / / _\` |  _____   / __/ _ \| '_ \` _ \| '_ \` _ \ / _ \| '_ \/ __|"
echo "  | | (_| |\ V / (_| | |     | ( (_| (_) | | | | | | | | | | | (_) | | | \__ \""
echo "  | |\__,_| \_/ \__,_|  ¯¯¯¯¯   \___\___/|_| |_| |_|_| |_| |_|\___/|_| |_|___/"
echo " _/ |"
echo "|__/            A tool to make your life easier, or harder ¯\_(ツ)_/¯ "
echo
sleep 2

# go to where the bash file was executed from (should be the main project directory)
APP_PATH="`dirname \"$0\"`"
cd $APP_PATH || exit

# script globals
hasError=false

# make the deploy folder, start from scratch
rm -rf deploy
mkdir deploy

# get the version
regex='^[0-9]+\.[0-9]+\.[0-9]+$'
echo "What version is this? (XX.XX.XX)"
printf "  : "
read -r version
if [[ ! $version =~ $regex ]]
then
  echo
  echo "Invalid input, exiting!"
  echo
  exit
fi
echo

# wait for the user to make the javadoc
echo "We need to create the javadoc first, go to Tools -> Generate Javadoc... and leave everything default and hit OK"
printf "Press enter after the javadoc was created to continue... "
read -r
echo

# grab the jar
printf "Moving built jar into deploy directory..."
cp $APP_PATH/out/artifacts/java_commons_jar/java-commons.jar $APP_PATH/deploy
echo "Done."

# check if the user is a dirty liar
printf "Verifying javadoc..."
javadocIndex=$APP_PATH/out/javadoc/index.html
javadocCom=$APP_PATH/out/javadoc/com
if [[ ! -f $javadocIndex || ! -d $javadocCom ]]
then
  printf "\nIt doesn't look like the javadoc was created, exiting!\n"
  exit
fi
echo "Done."

# bundle the new javadoc
printf "Bundling generated javadoc..."
cd $APP_PATH/out/javadoc || exit
jar cf $APP_PATH/deploy/java-commons-javadoc.jar *
echo "Done."

# check to see if everything is there
printf "Verifying jars..."
mainJar=$APP_PATH/deploy/java-commons.jar
javadocJar=$APP_PATH/deploy/java-commons-javadoc.jar
if [[ ! -f $mainJar ]]
then
  printf "\nMain library jar (java-commons.jar) wasn't found!"
  hasError=true
fi
if [[ ! -f $javadocJar ]]
then
  printf "\nJavadoc library jar (java-commons-javadoc.jar) wasn't found!"
  hasError=true
fi
if [[ $hasError == true ]]
then
  printf "\nExiting!\n"
  exit
fi
echo "Done."

# rename them to match version
printf "Setting version on libraries..."
cd $APP_PATH/deploy || exit
cp java-commons.jar java-commons-$version.jar
cp java-commons-javadoc.jar java-commons-$version-javadoc.jar

# verify everything's good to go
mainJar=$APP_PATH/deploy/java-commons-$version.jar
javadocJar=$APP_PATH/deploy/java-commons-$version-javadoc.jar
if [[ ! -f $mainJar ]]
then
  printf "\nError while renaming main jar!"
  hasError=true
fi
if [[ ! -f $javadocJar ]]
then
  printf "\nError while renaming javadoc jar!"
  hasError=true
fi
if [[ $hasError == true ]]
then
  printf "\nExiting!\n"
  exit
fi
echo "Done."

# clean up
printf "Cleaning up..."
cd $APP_PATH || exit
rm -rf out/artifacts
rm -rf out/javadoc
rm deploy/java-commons.jar
rm deploy/java-commons-javadoc.jar
echo "Done."

# all done, open the folder
echo
echo "Library packaged successfully!"
echo
cd $APP_PATH/deploy || exit
open .