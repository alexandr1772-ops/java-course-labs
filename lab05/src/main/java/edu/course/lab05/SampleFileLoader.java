package edu.course.lab05;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class SampleFileLoader {
    private static final String HEADER = "id;label;source;featureCount;status";

    public LoadResult load(Path path) {
        Objects.requireNonNull(path, "Path is required");

        List<DataSample> samples = new ArrayList<>();
        List<ValidationError> errors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null) {
                errors.add(new ValidationError(1, "header", "Header is missing"));
                return new LoadResult(samples, errors);
            }
            if (header.startsWith("\uFEFF")) {
                header = header.substring(1);
            }
            if (!HEADER.equals(header)) {
                errors.add(new ValidationError(1, "header", "Expected: " + HEADER));
                return new LoadResult(samples, errors);
            }

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (!line.isBlank()) {
                    parseLine(line, lineNumber, samples, errors);
                }
            }
        } catch (IOException cause) {
            throw new DataLoadingException("Cannot read " + path, cause);
        }

        return new LoadResult(samples, errors);
    }

    private void parseLine(String line, int lineNumber,
                           List<DataSample> samples, List<ValidationError> errors) {
        String[] fields = line.split(";", -1);
        if (fields.length != 5) {
            errors.add(new ValidationError(lineNumber, "row", "Expected 5 fields, got " + fields.length));
            return;
        }

        String id = fields[0].strip();
        String label = fields[1].strip();
        String source = fields[2].strip();
        String countText = fields[3].strip();
        String statusText = fields[4].strip();

        if (id.isBlank()) {
            errors.add(new ValidationError(lineNumber, "id", "Id cannot be empty"));
            return;
        }
        if (label.isBlank()) {
            errors.add(new ValidationError(lineNumber, "label", "Label cannot be empty"));
            return;
        }
        if (source.isBlank()) {
            errors.add(new ValidationError(lineNumber, "source", "Source cannot be empty"));
            return;
        }
        if (countText.isBlank()) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Feature count cannot be empty"));
            return;
        }
        if (statusText.isBlank()) {
            errors.add(new ValidationError(lineNumber, "status", "Status cannot be empty"));
            return;
        }

        int featureCount;
        try {
            featureCount = Integer.parseInt(countText);
        } catch (NumberFormatException cause) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Feature count must be an integer"));
            return;
        }
        if (featureCount <= 0) {
            errors.add(new ValidationError(lineNumber, "featureCount", "Feature count must be positive"));
            return;
        }

        SampleStatus status;
        try {
            status = SampleStatus.valueOf(statusText);
        } catch (IllegalArgumentException cause) {
            errors.add(new ValidationError(lineNumber, "status", "Unknown status: " + statusText));
            return;
        }

        samples.add(new DataSample(id, label, source, featureCount, status));
    }
}
