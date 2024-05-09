# TOPS Technical Exercise

## EXERCISE
See the attached file TOPS SW Technical exercise.
It contains a description of the exercise and the data file to be used.

## HOW TO RUN
### Requisites
- Java 11

### Steps
If you have and IDE such as IntelliJ IDEA, you can import the project and run it from there. 
Otherwise, you can run the project from the command line
following the instructions below.

1. Clone the repository
2. Navigate to the project root directory
3. Run the following command:
```shell
./gradlew run  (or .\gradlew run on Windows)

or 

./gradlew test (or .\gradlew test on Windows) to test the project
```

### HOW TO USE
If you select to run the default settings, the program will read the data from the file `document.txt` 
and will output the results to a file called `output.txt` located in the same folder.

If you select to enter custom settings, then you will be required to:
1. Enter the absolute path to the file containing the data
2. Enter the name of the output file
3. Enter the number of lines per page
4. Enter the number of characters per line

After that, the program will process the data and place the output file in the `src/main/resources` folder.