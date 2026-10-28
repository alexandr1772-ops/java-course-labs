package edu.course.lab03;

public class DataSample {
    private final String id;
    private final double[] features;

    public DataSample(String id, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features cannot be empty");
        }
        for (double value : features) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Features must be finite numbers");
            }
        }
        this.id = id;
        this.features = features.clone();
    }

    public String getId() {
        return id;
    }

    public double[] getFeatures() {
        return features.clone();
    }
}
