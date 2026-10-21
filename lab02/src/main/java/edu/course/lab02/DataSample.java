package edu.course.lab02;

public class DataSample {
    private final String id;
    private final String label;
    private SampleStatus status;
    private final double[] features;

    public DataSample(String id, String label, SampleStatus status, double[] features) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features cannot be empty");
        }
        for (double feature : features) {
            if (Double.isNaN(feature) || Double.isInfinite(feature)) {
                throw new IllegalArgumentException("Features must be finite numbers");
            }
        }

        this.id = id;
        this.label = label;
        this.status = status;
        this.features = features.clone();
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public SampleStatus getStatus() {
        return status;
    }

    public double[] getFeatures() {
        return features.clone();
    }

    public void changeStatus(SampleStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        status = newStatus;
    }

    public boolean isReady() {
        return status == SampleStatus.READY;
    }

    public double averageFeatures() {
        double sum = 0;
        for (double feature : features) {
            sum += feature;
        }
        return sum / features.length;
    }
}
