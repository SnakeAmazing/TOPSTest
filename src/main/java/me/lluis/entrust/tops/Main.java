package me.lluis.entrust.tops;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        // Get file
        Path file = Path.of("src/main/resources/document.txt");

        // Read file: It only has one line

        String line = "";
        try {
            line = Files.readAllLines(file).get(0);
        } catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
        }

        String[] data = line.split(" ");

        Path output = Path.of("src/main/resources/entrust-tops-output.txt");
        try {
            if (Files.exists(output)) Files.delete(output);

            Files.createFile(output);

            BufferedWriter writer = Files.newBufferedWriter(output);

            int page = 1;
            int lines = 1;
            int chars = 0;

            writer.write("Page " + page + "\n\n");

            for (String word : data) {
                int wordLength = word.length();

                if (chars + wordLength > 80) {
                    // New line
                    lines++;

                    if (lines > 25) {
                        // New page
                        page++;
                        lines = 1;

                        writer.write("\n\n");
                        writer.write("Page " + page);
                        writer.write("\n\n");
                    } else {
                        writer.write("\n");
                    }
                    chars = 0;
                    writer.write(word);
                    continue;
                }

                chars += wordLength;
                writer.write(word + " ");
            }

            writer.close();
        } catch (IOException exception) {
            System.out.println("Error creating file: " + exception.getMessage());
        }
    }
}