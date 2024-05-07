package me.lluis.entrust.tops;

import me.lluis.entrust.tops.file.FileParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileParserTest {

    @Test
    public void testBuilder() {
        Path inputPath = Path.of("input.txt");
        Path expected = Path.of("output.txt");

        FileParser parser = FileParser.builder()
                .inputPath(inputPath)
                .outputPath(expected)
                .linesPerPage(25)
                .charsPerLine(80)
                .build();

        Assertions.assertEquals(inputPath, parser.getInputPath());
        Assertions.assertEquals(expected, parser.getOutputPath());
        Assertions.assertEquals(25, parser.getLinesPerPage());
        Assertions.assertEquals(80, parser.getCharsPerLine());
    }

    @Test
    public void testParse() {
        Path output = Path.of("src/test/resources/test-output.txt");

        if (Files.exists(output)) {
            try {
                Files.delete(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileParser parser = FileParser.builder()
                .inputPath(Path.of("src/main/resources/document.txt"))
                .outputPath(output)
                .linesPerPage(25)
                .charsPerLine(80)
                .build();

        Path result = parser.parse();

        Assertions.assertEquals(output, result);
        Path realResult = Path.of("src/test/resources/output.txt");

        try {
            Assertions.assertEquals(Files.readString(realResult), Files.readString(output));

            long sizeOutput = Files.size(output);
            long sizeRealOutput = Files.size(realResult);
            Assertions.assertEquals(sizeRealOutput, sizeOutput);


            Files.delete(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithDifferentSettings() {
        Path output1 = Path.of("src/test/resources/test-output1.txt");
        Path output2 = Path.of("src/test/resources/test-output2.txt");

        if (Files.exists(output1) || Files.exists(output2)) {
            try {
                Files.delete(output1);
                Files.delete(output2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path inputPath = Path.of("src/main/resources/document.txt");

        FileParser parser = FileParser.builder()
                .inputPath(inputPath)
                .outputPath(output1)
                .linesPerPage(10)
                .charsPerLine(40)
                .build();

        Path result1 = parser.parse();

        FileParser parser2 = FileParser.builder()
                .inputPath(inputPath)
                .outputPath(output2)
                .linesPerPage(10)
                .charsPerLine(40)
                .build();

        Path result2 = parser2.parse();

        try {
            Assertions.assertEquals(Files.readString(result1), Files.readString(result2));

            long sizeOutput = Files.size(result1);
            long sizeRealOutput = Files.size(result2);
            Assertions.assertEquals(sizeRealOutput, sizeOutput);

            Files.delete(result1);
            Files.delete(result2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
