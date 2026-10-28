package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class MutableKeyExperimentTest {
    private static final class MutableKey {
        private int number;

        MutableKey(int number) {
            this.number = number;
        }

        void setNumber(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof MutableKey other)) return false;
            return number == other.number;
        }

        @Override
        public int hashCode() {
            return number;
        }
    }

    @Test
    void changingAStoredKeyBreaksLookup() {
        Map<MutableKey, String> map = new HashMap<>();
        MutableKey key = new MutableKey(1);
        map.put(key, "sample");
        assertEquals("sample", map.get(key));
        key.setNumber(2);
        assertNull(map.get(key));
        assertEquals(1, map.size());
    }
}
