package edu.course.lab04;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class SampleRegistry {
    private final Map<SampleId, DataSample> samples = new HashMap<>();

    public DataSample put(SampleId id, DataSample sample) {
        return samples.put(Objects.requireNonNull(id, "Sample ID is required"),
                Objects.requireNonNull(sample, "Sample is required"));
    }

    public DataSample get(SampleId id) {
        return samples.get(Objects.requireNonNull(id, "Sample ID is required"));
    }

    public DataSample remove(SampleId id) {
        return samples.remove(Objects.requireNonNull(id, "Sample ID is required"));
    }

    public boolean containsKey(SampleId id) {
        return samples.containsKey(Objects.requireNonNull(id, "Sample ID is required"));
    }

    public int size() {
        return samples.size();
    }
}
