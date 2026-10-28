package edu.course.lab04;

import java.util.Objects;

public final class SampleId {
    private final String datasetName;
    private final int number;

    public SampleId(String datasetName, int number) {
        if (datasetName == null || datasetName.isBlank()) {
            throw new IllegalArgumentException("Dataset name cannot be empty");
        }
        if (number < 0) {
            throw new IllegalArgumentException("Sample number cannot be negative");
        }
        this.datasetName = datasetName;
        this.number = number;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SampleId other)) return false;
        return number == other.number && datasetName.equals(other.datasetName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(datasetName, number);
    }

    @Override
    public String toString() {
        return datasetName + ":" + number;
    }
}
