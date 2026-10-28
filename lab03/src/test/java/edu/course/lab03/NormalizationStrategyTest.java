package edu.course.lab03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalizationStrategyTest {
    @Test
    void minMaxNormalizesThreeNumbers() {
        double[] values = {10, 20, 30};
        assertArrayEquals(new double[]{0, 0.5, 1}, new MinMaxNormalization().normalize(values), 0.000001);
    }

    @Test
    void minMaxWorksWithNegativeNumbers() {
        double[] values = {-5, 0, 5};
        assertArrayEquals(new double[]{0, 0.5, 1}, new MinMaxNormalization().normalize(values), 0.000001);
    }

    @Test
    void minMaxReturnsZerosForEqualNumbers() {
        assertArrayEquals(new double[]{0, 0, 0},
                new MinMaxNormalization().normalize(new double[]{7, 7, 7}), 0.000001);
    }

    @Test
    void minMaxDoesNotChangeInput() {
        double[] values = {10, 20, 30};
        double[] result = new MinMaxNormalization().normalize(values);
        result[0] = 99;
        assertArrayEquals(new double[]{10, 20, 30}, values, 0.000001);
    }

    @Test
    void meanCenteringSubtractsMean() {
        assertArrayEquals(new double[]{-10, 0, 10},
                new MeanCenteringNormalization().normalize(new double[]{10, 20, 30}), 0.000001);
    }

    @Test
    void meanCenteringHandlesNegativeNumbers() {
        assertArrayEquals(new double[]{-3, 0, 3},
                new MeanCenteringNormalization().normalize(new double[]{-3, 0, 3}), 0.000001);
    }

    @Test
    void meanCenteringDoesNotChangeInput() {
        double[] values = {2, 4, 6};
        double[] result = new MeanCenteringNormalization().normalize(values);
        result[0] = 100;
        assertArrayEquals(new double[]{2, 4, 6}, values, 0.000001);
    }

    @Test
    void strategiesRejectInvalidValues() {
        NormalizationStrategy minMax = new MinMaxNormalization();
        NormalizationStrategy meanCentering = new MeanCenteringNormalization();
        assertThrows(IllegalArgumentException.class, () -> minMax.normalize(null));
        assertThrows(IllegalArgumentException.class, () -> minMax.normalize(new double[0]));
        assertThrows(IllegalArgumentException.class, () -> minMax.normalize(new double[]{Double.NaN}));
        assertThrows(IllegalArgumentException.class, () -> meanCentering.normalize(null));
        assertThrows(IllegalArgumentException.class, () -> meanCentering.normalize(new double[0]));
        assertThrows(IllegalArgumentException.class,
                () -> meanCentering.normalize(new double[]{Double.POSITIVE_INFINITY}));
    }
}
