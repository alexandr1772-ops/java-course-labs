package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataSampleTest {
    @Test
    void createsValidSample() {
        DataSample sample = new DataSample("sample-1", "cat", SampleStatus.NEW, new double[]{2, 4, 6});
        assertEquals("sample-1", sample.getId());
        assertEquals("cat", sample.getLabel());
        assertEquals(SampleStatus.NEW, sample.getStatus());
        assertFalse(sample.isReady());
        assertEquals(4.0, sample.averageFeatures(), 0.0001);
    }

    @Test
    void changesStatus() {
        DataSample sample = new DataSample("1", "dog", SampleStatus.NEW, new double[]{1});
        sample.changeStatus(SampleStatus.READY);
        assertEquals(SampleStatus.READY, sample.getStatus());
        assertTrue(sample.isReady());
        sample.changeStatus(SampleStatus.IN_PROGRESS);
        assertFalse(sample.isReady());
    }

    @Test
    void rejectsEmptyIdAndLabel() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample(null, "cat", SampleStatus.NEW, new double[]{1}));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("  ", "cat", SampleStatus.NEW, new double[]{1}));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", null, SampleStatus.NEW, new double[]{1}));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", " ", SampleStatus.NEW, new double[]{1}));
    }

    @Test
    void rejectsInvalidStatus() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", "cat", null, new double[]{1}));
        DataSample sample = new DataSample("1", "cat", SampleStatus.NEW, new double[]{1});
        assertThrows(IllegalArgumentException.class, () -> sample.changeStatus(null));
        assertEquals(SampleStatus.NEW, sample.getStatus());
    }

    @Test
    void rejectsInvalidFeatures() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", "cat", SampleStatus.NEW, null));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", "cat", SampleStatus.NEW, new double[]{}));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", "cat", SampleStatus.NEW, new double[]{Double.NaN}));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("1", "cat", SampleStatus.NEW, new double[]{Double.POSITIVE_INFINITY}));
    }

    @Test
    void protectsFeaturesFromChanges() {
        double[] numbers = {2, 4, 6};
        DataSample sample = new DataSample("1", "cat", SampleStatus.NEW, numbers);
        numbers[0] = 100;
        double[] copy = sample.getFeatures();
        copy[1] = 100;
        assertArrayEquals(new double[]{2, 4, 6}, sample.getFeatures());
        assertEquals(4.0, sample.averageFeatures(), 0.0001);
    }

    @Test
    void calculatesAverageWithNegativeNumbers() {
        DataSample sample = new DataSample("1", "cat", SampleStatus.READY, new double[]{-2, 0, 5});
        assertEquals(1.0, sample.averageFeatures(), 0.0001);
    }
}
