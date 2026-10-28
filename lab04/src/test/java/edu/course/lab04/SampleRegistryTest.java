package edu.course.lab04;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SampleRegistryTest {
    @Test
    void equalKeyInstancesFindOneSample() {
        SampleRegistry registry = new SampleRegistry();
        DataSample sample = new DataSample("s1", new double[]{1, 2});
        registry.put(new SampleId("train", 1), sample);
        assertSame(sample, registry.get(new SampleId("train", 1)));
        assertTrue(registry.containsKey(new SampleId("train", 1)));
    }

    @Test
    void equalKeyReplacesWithoutIncreasingSize() {
        SampleRegistry registry = new SampleRegistry();
        DataSample first = new DataSample("s1", new double[]{1});
        DataSample second = new DataSample("s2", new double[]{2});
        assertNull(registry.put(new SampleId("train", 1), first));
        assertSame(first, registry.put(new SampleId("train", 1), second));
        assertEquals(1, registry.size());
        assertSame(second, registry.get(new SampleId("train", 1)));
    }

    @Test
    void removeAndMissingKeyWork() {
        SampleRegistry registry = new SampleRegistry();
        SampleId key = new SampleId("test", 2);
        DataSample sample = new DataSample("s", new double[]{5});
        assertNull(registry.get(key));
        assertFalse(registry.containsKey(key));
        registry.put(key, sample);
        assertSame(sample, registry.remove(new SampleId("test", 2)));
        assertEquals(0, registry.size());
        assertNull(registry.remove(key));
    }

    @Test
    void collidingKeysKeepDifferentSamples() {
        SampleRegistry registry = new SampleRegistry();
        SampleId first = new SampleId("Aa", 3);
        SampleId second = new SampleId("BB", 3);
        assertEquals(first.hashCode(), second.hashCode());
        DataSample a = new DataSample("a", new double[]{1});
        DataSample b = new DataSample("b", new double[]{2});
        registry.put(first, a);
        registry.put(second, b);
        assertEquals(2, registry.size());
        assertSame(a, registry.get(first));
        assertSame(b, registry.get(second));
    }

    @Test
    void rejectsNullKeysAndValues() {
        SampleRegistry registry = new SampleRegistry();
        SampleId key = new SampleId("train", 1);
        assertThrows(NullPointerException.class, () -> registry.put(null, new DataSample("s", new double[]{1})));
        assertThrows(NullPointerException.class, () -> registry.put(key, null));
        assertThrows(NullPointerException.class, () -> registry.get(null));
        assertThrows(NullPointerException.class, () -> registry.remove(null));
        assertThrows(NullPointerException.class, () -> registry.containsKey(null));
    }

    @Test
    void dataSampleCopiesFeatures() {
        double[] values = {1, 2};
        DataSample sample = new DataSample("s", values);
        values[0] = 99;
        double[] returned = sample.getFeatures();
        returned[1] = 99;
        assertArrayEquals(new double[]{1, 2}, sample.getFeatures());
    }
}
