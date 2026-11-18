package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumbersPreparationTest {
    @TempDir
    Path tempDir;

    @Test
    void readsUtf8FileAndKeepsValidNumbers() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(List.of("10", "bad", "20"), lines);

        List<Integer> numbers = new ArrayList<>();
        for (String line : lines) {
            try {
                numbers.add(Integer.parseInt(line));
            } catch (NumberFormatException ignored) {
            }
        }
        assertEquals(List.of(10, 20), numbers);
    }

    @Test
    void invalidNumberThrowsNumberFormatException() throws IOException {
        Path file = tempDir.resolve("numbers.txt");
        Files.write(file, List.of("10", "bad", "20"), StandardCharsets.UTF_8);

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertThrows(NumberFormatException.class,
                () -> Integer.parseInt(lines.get(1)));
    }
}
