package edu.course.lab06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleAnalyticsTest {
    private final SampleAnalytics analytics = new SampleAnalytics();

    private static DataSample sample(String id, String source, int featureCount,
                                     SampleStatus status) {
        return new DataSample(id, "cat", source, featureCount, status);
    }

    @Test
    void countsSamplesByStatus() {
        List<DataSample> samples = List.of(
                sample("S-1", "camera-1", 10, SampleStatus.READY),
                sample("S-2", "camera-2", 20, SampleStatus.READY),
                sample("S-3", "camera-1", 30, SampleStatus.DONE),
                sample("S-4", "camera-3", 40, SampleStatus.IN_PROGRESS));

        Map<SampleStatus, Integer> counts = analytics.countByStatus(samples);
        assertEquals(Integer.valueOf(2), counts.get(SampleStatus.READY));
        assertEquals(Integer.valueOf(1), counts.get(SampleStatus.DONE));
        assertEquals(Integer.valueOf(1), counts.get(SampleStatus.IN_PROGRESS));
        assertEquals(3, counts.size());
    }

    @Test
    void countsOnlyStatusesPresent() {
        Map<SampleStatus, Integer> counts = analytics.countByStatus(List.of(
                sample("S-1", "camera", 4, SampleStatus.READY)));
        assertEquals(Integer.valueOf(1), counts.get(SampleStatus.READY));
        assertFalse(counts.containsKey(SampleStatus.DONE));
    }

    @Test
    void returnsUniqueSourcesPreservingFirstAppearanceOrder() {
        List<DataSample> samples = List.of(
                sample("S-1", "camera-b", 3, SampleStatus.READY),
                sample("S-2", "camera-a", 4, SampleStatus.READY),
                sample("S-3", "camera-b", 5, SampleStatus.DONE));

        Set<String> sources = analytics.uniqueSources(samples);
        assertEquals(List.of("camera-b", "camera-a"), new ArrayList<>(sources));
        assertEquals(2, sources.size());
    }

    @Test
    void sortsByFeatureCountThenIdentifier() {
        DataSample tenB = sample("B", "camera", 10, SampleStatus.READY);
        DataSample twenty = sample("C", "camera", 20, SampleStatus.DONE);
        DataSample tenA = sample("A", "camera", 10, SampleStatus.IN_PROGRESS);
        DataSample five = sample("D", "camera", 5, SampleStatus.READY);

        List<DataSample> sorted = analytics.sortedByFeatureCountAndId(
                List.of(tenB, twenty, tenA, five));
        assertEquals(List.of(five, tenA, tenB, twenty), sorted);
    }

    @Test
    void sortingDoesNotChangeOriginalList() {
        DataSample first = sample("X", "camera", 100, SampleStatus.READY);
        DataSample second = sample("Y", "camera", 1, SampleStatus.DONE);
        List<DataSample> original = new ArrayList<>(List.of(first, second));

        List<DataSample> result = analytics.sortedByFeatureCountAndId(original);
        assertNotSame(original, result);
        assertEquals(List.of(first, second), original);
        assertEquals(List.of(second, first), result);
    }

    @Test
    void emptyInputProducesEmptyCollections() {
        assertTrue(analytics.countByStatus(List.of()).isEmpty());
        assertTrue(analytics.uniqueSources(List.of()).isEmpty());
        assertTrue(analytics.sortedByFeatureCountAndId(List.of()).isEmpty());
    }

    @Test
    void methodsRejectNullList() {
        assertThrows(NullPointerException.class, () -> analytics.countByStatus(null));
        assertThrows(NullPointerException.class, () -> analytics.uniqueSources(null));
        assertThrows(NullPointerException.class, () -> analytics.sortedByFeatureCountAndId(null));
    }

    @Test
    void methodsRejectNullElements() {
        List<DataSample> samples = Arrays.asList(
                sample("S-1", "camera", 3, SampleStatus.READY), null);
        assertThrows(NullPointerException.class, () -> analytics.countByStatus(samples));
        assertThrows(NullPointerException.class, () -> analytics.uniqueSources(samples));
        assertThrows(NullPointerException.class, () -> analytics.sortedByFeatureCountAndId(samples));
    }

    @Test
    void dataSampleValidatesItsFields() {
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("", "cat", "camera", 2, SampleStatus.READY));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", " ", "camera", 2, SampleStatus.READY));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", "", 2, SampleStatus.READY));
        assertThrows(IllegalArgumentException.class,
                () -> new DataSample("S-1", "cat", "camera", 0, SampleStatus.READY));
        assertThrows(NullPointerException.class,
                () -> new DataSample("S-1", "cat", "camera", 2, null));
    }
}
