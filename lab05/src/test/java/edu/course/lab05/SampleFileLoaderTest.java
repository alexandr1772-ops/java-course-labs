package edu.course.lab05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SampleFileLoaderTest {
    @TempDir
    Path tempDir;

    private Path writeFile(String... lines) throws IOException {
        Path file = tempDir.resolve("samples.txt");
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void loadsValidFile() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-101;cat;camera-1;128;READY",
                "S-102;dog;camera-2;64;DONE"
        );

        LoadResult result = new SampleFileLoader().load(file);

        assertEquals(2, result.getSamples().size());
        assertTrue(result.getErrors().isEmpty());
        DataSample first = result.getSamples().get(0);
        assertEquals("S-101", first.getId());
        assertEquals("cat", first.getLabel());
        assertEquals("camera-1", first.getSource());
        assertEquals(128, first.getFeatureCount());
        assertEquals(SampleStatus.READY, first.getStatus());
    }

    @Test
    void loadsUnicodeText() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;кошка;камера-1;3;IN_PROGRESS"
        );

        LoadResult result = new SampleFileLoader().load(file);

        assertEquals("кошка", result.getSamples().get(0).getLabel());
        assertEquals("камера-1", result.getSamples().get(0).getSource());
        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    void rejectsEmptyRequiredFields() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                ";cat;camera-1;128;READY",
                "S-2;;camera-1;128;READY",
                "S-3;cat;;128;READY",
                "S-4;cat;camera-1;;READY",
                "S-5;cat;camera-1;128;"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertEquals(0, result.getSamples().size());
        assertEquals(5, result.getErrors().size());
        assertEquals("id", result.getErrors().get(0).getField());
        assertEquals("label", result.getErrors().get(1).getField());
        assertEquals("source", result.getErrors().get(2).getField());
        assertEquals("featureCount", result.getErrors().get(3).getField());
        assertEquals("status", result.getErrors().get(4).getField());
    }

    @Test
    void rejectsIncorrectNumbers() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;cat;camera;bad;READY",
                "S-2;cat;camera;0;READY",
                "S-3;cat;camera;-4;READY",
                "S-4;cat;camera;9999999999999;READY"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertTrue(result.getSamples().isEmpty());
        assertEquals(4, result.getErrors().size());
        for (ValidationError error : result.getErrors()) {
            assertEquals("featureCount", error.getField());
        }
    }

    @Test
    void rejectsUnknownStatus() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;cat;camera;128;UNKNOWN"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(2, result.getErrors().get(0).getLineNumber());
        assertEquals("status", result.getErrors().get(0).getField());
    }

    @Test
    void rejectsIncorrectFieldCount() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;cat;camera;128",
                "S-2;cat;camera;128;READY;extra"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertTrue(result.getSamples().isEmpty());
        assertEquals(2, result.getErrors().size());
        assertEquals("row", result.getErrors().get(0).getField());
        assertEquals("row", result.getErrors().get(1).getField());
    }

    @Test
    void keepsValidRowsAndTracksPhysicalLineNumbers() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;cat;camera;12;READY",
                "",
                "S-2;dog;camera;invalid;READY",
                "S-3;fox;camera;8;DONE",
                "S-4;bird;camera;7;WRONG",
                "S-5;owl;camera;5;READY"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertEquals(3, result.getSamples().size());
        assertEquals("S-1", result.getSamples().get(0).getId());
        assertEquals("S-3", result.getSamples().get(1).getId());
        assertEquals("S-5", result.getSamples().get(2).getId());
        assertEquals(2, result.getErrors().size());
        assertEquals(4, result.getErrors().get(0).getLineNumber());
        assertEquals(6, result.getErrors().get(1).getLineNumber());
    }

    @Test
    void missingFilePreservesIoCause() {
        Path missing = tempDir.resolve("does-not-exist.txt");

        DataLoadingException exception = assertThrows(
                DataLoadingException.class,
                () -> new SampleFileLoader().load(missing)
        );

        assertNotNull(exception.getCause());
        assertInstanceOf(IOException.class, exception.getCause());
        assertTrue(exception.getMessage().contains("does-not-exist.txt"));
    }

    @Test
    void invalidHeaderIsReportedSeparately() throws IOException {
        Path file = writeFile(
                "name;label;source;featureCount;status",
                "S-1;cat;camera;12;READY"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertTrue(result.getSamples().isEmpty());
        assertEquals(1, result.getErrors().size());
        assertEquals(1, result.getErrors().get(0).getLineNumber());
        assertEquals("header", result.getErrors().get(0).getField());
    }

    @Test
    void emptyFileReportsMissingHeader() throws IOException {
        Path file = tempDir.resolve("empty.txt");
        Files.writeString(file, "", StandardCharsets.UTF_8);

        LoadResult result = new SampleFileLoader().load(file);
        assertTrue(result.getSamples().isEmpty());
        assertEquals("header", result.getErrors().get(0).getField());
    }

    @Test
    void resultListsCannotBeModified() throws IOException {
        Path file = writeFile(
                "id;label;source;featureCount;status",
                "S-1;cat;camera;12;READY",
                "S-2;dog;camera;bad;READY"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertThrows(UnsupportedOperationException.class, () -> result.getSamples().clear());
        assertThrows(UnsupportedOperationException.class, () -> result.getErrors().clear());
    }

    @Test
    void acceptsUtf8BomAtStartOfHeader() throws IOException {
        Path file = writeFile(
                "\uFEFFid;label;source;featureCount;status",
                "S-1;cat;camera;12;READY"
        );

        LoadResult result = new SampleFileLoader().load(file);
        assertEquals(1, result.getSamples().size());
        assertTrue(result.getErrors().isEmpty());
    }
}
