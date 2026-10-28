package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SampleIdTest {
    @Test
    void equalKeysHaveEqualHashCodes() {
        SampleId first = new SampleId("train", 7);
        SampleId second = new SampleId("train", 7);
        assertNotSame(first, second);
        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentFieldsProduceDifferentKeys() {
        SampleId id = new SampleId("train", 7);
        assertNotEquals(id, new SampleId("test", 7));
        assertNotEquals(id, new SampleId("train", 8));
    }

    @Test
    void equalityRejectsNullAndOtherType() {
        SampleId id = new SampleId("train", 7);
        assertNotEquals(null, id);
        assertNotEquals("train:7", id);
        assertEquals(id, id);
    }

    @Test
    void collisionsDoNotLoseDifferentKeys() {
        SampleId first = new SampleId("Aa", 3);
        SampleId second = new SampleId("BB", 3);
        assertNotEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        Map<SampleId, String> values = new HashMap<>();
        values.put(first, "first");
        values.put(second, "second");
        assertEquals(2, values.size());
        assertEquals("first", values.get(first));
        assertEquals("second", values.get(second));
    }

    @Test
    void validatesConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new SampleId(null, 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId(" ", 1));
        assertThrows(IllegalArgumentException.class, () -> new SampleId("train", -1));
    }
}
