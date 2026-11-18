package edu.course.lab06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxTest {

    @Test
    void storesAndReplacesString() {
        Box<String> box = new Box<>("first");
        assertEquals("first", box.get());

        box.set("second");
        assertEquals("second", box.get());

        // box.set(42); // Does not compile: Box<String> cannot accept int.
    }

    @Test
    void storesAndReplacesInteger() {
        Box<Integer> box = new Box<>(10);
        assertEquals(Integer.valueOf(10), box.get());

        box.set(20);
        assertEquals(Integer.valueOf(20), box.get());
    }
}
