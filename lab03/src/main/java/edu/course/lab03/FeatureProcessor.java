package edu.course.lab03;

import java.util.Objects;

public class FeatureProcessor {
    private final NormalizationStrategy strategy;

    public FeatureProcessor(NormalizationStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Normalization strategy is required");
    }

    public double[] process(double[] values) {
        return strategy.normalize(values);
    }

    public double[] process(DataSample sample) {
        Objects.requireNonNull(sample, "Sample is required");
        return strategy.normalize(sample.getFeatures());
    }
}
