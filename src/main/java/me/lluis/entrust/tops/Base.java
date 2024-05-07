package me.lluis.entrust.tops;

import me.lluis.entrust.tops.file.FileParser;

import java.nio.file.Path;
import java.util.Scanner;

import static java.lang.System.exit;

public class Base {

    private Scanner scanner;

    public void start() {
        scanner = new Scanner(System.in);
        System.out.println("Welcome to the TOPS test exercise!");

        while (true) {
            scanner.reset();
            System.out.println("Do you want to use the default values? (y/n) (Type 'exit' to quit.)");
            String response = scanner.next();

            if (response.equalsIgnoreCase("exit")) {
                stop();
                return;
            }

            while (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
                System.out.println("Invalid response. Please type 'y' or 'n'.");
                response = scanner.nextLine();
            }

            if (response.equalsIgnoreCase("y")) {
                useDefaultSettings();
                continue;
            }

            // Response == n
            System.out.println("Please enter the path to the input file:");
            String inputPath = scanner.next();

            System.out.println("Please enter the name of the output file " +
                    "(it will be placed in the project directory):");

            String outputPath = scanner.next();
            if (!outputPath.contains(".")) outputPath += ".txt";

            System.out.println("Please enter the number of lines per page:");
            int linesPerPage = scanner.nextInt();

            System.out.println("Please enter the number of characters per line:");
            int charsPerLine = scanner.nextInt();

            FileParser parser = FileParser.builder()
                    .inputPath(Path.of(inputPath))
                    .outputPath(Path.of("src/main/resources/" + outputPath))
                    .linesPerPage(linesPerPage)
                    .charsPerLine(charsPerLine)
                    .build();

            Path result = parser.parse();
            System.out.println("File parsed successfully. Output file: " + result);
            System.out.println();
        }
    }

    public void stop() {
        scanner.close();
        System.out.println("Goodbye!");
        exit(0);
    }

    private void useDefaultSettings() {
        System.out.println("Using default settings...");
        System.out.println("Input file: src/main/resources/document.txt");
        System.out.println("Output file: src/main/resources/output.txt");
        System.out.println("Lines per page: 25");
        System.out.println("Characters per line: 80");

        FileParser parser = FileParser.builder()
                .inputPath(Path.of("src/main/resources/document.txt"))
                .outputPath(Path.of("src/main/resources/output.txt"))
                .linesPerPage(25)
                .charsPerLine(80)
                .build();

        Path result = parser.parse();
        System.out.println("File parsed successfully. Output file: " + result);
        System.out.println();
    }
}
