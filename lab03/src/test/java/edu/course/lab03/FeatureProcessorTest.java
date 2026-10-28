package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeatureProcessorTest {
    @Test
    void processorWorksWithMinMax() {
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[]{0, 0.5, 1}, processor.process(new double[]{2, 4, 6}), 0.000001);
    }

    @Test
    void processorWorksWithMeanCentering() {
        FeatureProcessor processor = new FeatureProcessor(new MeanCenteringNormalization());
        assertArrayEquals(new double[]{-2, 0, 2}, processor.process(new double[]{2, 4, 6}), 0.000001);
    }

    @Test
    void processorAcceptsDataSample() {
        DataSample sample = new DataSample("sample-1", new double[]{10, 20, 30});
        FeatureProcessor processor = new FeatureProcessor(new MinMaxNormalization());
        assertArrayEquals(new double[]{0, 0.5, 1}, processor.process(sample), 0.000001);
        assertArrayEquals(new double[]{10, 20, 30}, sample.getFeatures(), 0.000001);
    }

    @Test
    void processorRequiresStrategy() {
        assertThrows(NullPointerException.class, () -> new FeatureProcessor(null));
    }

    @Test
    void sampleProtectsItsFeatures() {
        double[] source = {2, 4, 6};
        DataSample sample = new DataSample("sample-2", source);
        source[0] = 99;
        double[] copy = sample.getFeatures();
        copy[1] = 99;
        assertArrayEquals(new double[]{2, 4, 6}, sample.getFeatures(), 0.000001);
        assertEquals("sample-2", sample.getId());
    }

    @Test
    void sampleRejectsInvalidData() {
        assertThrows(IllegalArgumentException.class, () -> new DataSample(" ", new double[]{1}));
        assertThrows(IllegalArgumentException.class, () -> new DataSample("sample", null));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("sample", new double[]{Double.NaN}));
    }
}
