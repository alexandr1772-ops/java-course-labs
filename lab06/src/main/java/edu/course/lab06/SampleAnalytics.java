package edu.course.lab06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class SampleAnalytics {

    public Map<SampleStatus, Integer> countByStatus(List<DataSample> samples) {
        Objects.requireNonNull(samples, "Samples are required");
        Map<SampleStatus, Integer> counts = new EnumMap<>(SampleStatus.class);

        for (DataSample sample : samples) {
            Objects.requireNonNull(sample, "Sample cannot be null");
            SampleStatus status = sample.getStatus();
            counts.put(status, counts.getOrDefault(status, 0) + 1);
        }

        return counts;
    }

    public Set<String> uniqueSources(List<DataSample> samples) {
        Objects.requireNonNull(samples, "Samples are required");
        Set<String> sources = new LinkedHashSet<>();

        for (DataSample sample : samples) {
            Objects.requireNonNull(sample, "Sample cannot be null");
            sources.add(sample.getSource());
        }

        return sources;
    }

    public List<DataSample> sortedByFeatureCountAndId(List<DataSample> samples) {
        Objects.requireNonNull(samples, "Samples are required");
        List<DataSample> sorted = new ArrayList<>();

        for (DataSample sample : samples) {
            sorted.add(Objects.requireNonNull(sample, "Sample cannot be null"));
        }

        sorted.sort(Comparator.comparingInt(DataSample::getFeatureCount)
                .thenComparing(DataSample::getId));
        return sorted;
    }
}
