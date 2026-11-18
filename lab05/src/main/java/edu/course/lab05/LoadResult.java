package edu.course.lab05;

import java.util.List;
import java.util.Objects;

public final class LoadResult {
    private final List<DataSample> samples;
    private final List<ValidationError> errors;

    public LoadResult(List<DataSample> samples, List<ValidationError> errors) {
        this.samples = List.copyOf(Objects.requireNonNull(samples, "Samples are required"));
        this.errors = List.copyOf(Objects.requireNonNull(errors, "Errors are required"));
    }

    public List<DataSample> getSamples() {
        return samples;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
