package me.lluis.entrust.tops.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileParser {

    /**
     * Path to the file to be parsed
     */
    private final Path inputPath;

    /**
     * Path to the output file
     */
    private final Path outputPath;

    /**
     * Number of lines per page
     */
    private final int linesPerPage;

    /**
     * Number of characters per line
     */
    private final int charsPerLine;

    /**
     * Constructor
     *
     * @param inputPath    Path to the file to be parsed
     * @param outputPath   Path to the output file
     * @param linesPerPage Number of lines per page
     * @param charsPerLine Number of characters per line
     */
    public FileParser(Path inputPath, Path outputPath, int linesPerPage, int charsPerLine) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
        this.linesPerPage = linesPerPage;
        this.charsPerLine = charsPerLine;
    }

    public Path parse() {
        String line = "";
        try {
            line = Files.readAllLines(inputPath).get(0);
        } catch (IOException exception) {
            System.out.println("Error reading file: " + exception.getMessage());
        }

        String[] data = line.split(" ");

        try {
            if (Files.exists(outputPath)) Files.delete(outputPath);

            Files.createFile(outputPath);

            BufferedWriter writer = Files.newBufferedWriter(outputPath);

            int page = 1;
            int lines = 1;
            int chars = 0;

            boolean first = true;
            for (String word : data) {
                int wordLength = word.length();

                if (chars + wordLength >= charsPerLine) {
                    // New line
                    lines++;

                    if (lines > linesPerPage) {
                        // New page
                        lines = 1;

                        writer.write("\n\n");
                        writer.write("Page " + page);
                        writer.write("\n\n");
                        ++page;
                    } else {
                        writer.write("\n");
                    }
                    chars = wordLength;
                    writer.write(word);
                    continue;
                }

                if (first) {
                    writer.write(word);
                    first = false;
                    chars += wordLength;
                } else {
                    writer.write(" " + word);
                    chars += wordLength + 1; // +1 for the space
                }
            }

            writer.write("\n\n");
            writer.write("Page " + page);
            writer.write("\n\n");
            writer.close();

        } catch (IOException exception) {
            System.out.println("Error creating file: " + exception.getMessage());
        }

        return outputPath;
    }

    public Path getInputPath() {
        return inputPath;
    }

    public Path getOutputPath() {
        return outputPath;
    }

    public int getLinesPerPage() {
        return linesPerPage;
    }

    public int getCharsPerLine() {
        return charsPerLine;
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for FileParser
     */
    public static class Builder {

        private Path inputPath;
        private Path outputPath;
        private int linesPerPage;
        private int charsPerLine;

        public Builder inputPath(Path inputPath) {
            this.inputPath = inputPath;
            return this;
        }

        public Builder outputPath(Path outputPath) {
            this.outputPath = outputPath;
            return this;
        }

        public Builder linesPerPage(int linesPerPage) {
            this.linesPerPage = linesPerPage;
            return this;
        }

        public Builder charsPerLine(int charsPerLine) {
            this.charsPerLine = charsPerLine;
            return this;
        }

        public FileParser build() {
            return new FileParser(inputPath, outputPath, linesPerPage, charsPerLine);
        }
    }
}
