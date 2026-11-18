package edu.course.lab06;

import java.util.Objects;

public final class DataSample {
    private final String id;
    private final String label;
    private final String source;
    private final int featureCount;
    private final SampleStatus status;

    public DataSample(String id, String label, String source,
                      int featureCount, SampleStatus status) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        if (featureCount <= 0) {
            throw new IllegalArgumentException("Feature count must be positive");
        }
        this.id = id;
        this.label = label;
        this.source = source;
        this.featureCount = featureCount;
        this.status = Objects.requireNonNull(status, "Status is required");
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getSource() {
        return source;
    }

    public int getFeatureCount() {
        return featureCount;
    }

    public SampleStatus getStatus() {
        return status;
    }
}
