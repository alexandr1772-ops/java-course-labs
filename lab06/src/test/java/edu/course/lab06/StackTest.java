package edu.course.lab06;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackTest {

    @Test
    void newlyCreatedStackIsEmpty() {
        Stack<String> stack = new Stack<>();
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    void pushAndPopFollowLastInFirstOut() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        assertEquals(3, stack.size());
        assertEquals("third", stack.pop());
        assertEquals("second", stack.pop());
        assertEquals("first", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    void peekDoesNotRemoveTopValue() {
        Stack<Integer> stack = new Stack<>();
        stack.push(4);
        stack.push(8);

        assertEquals(Integer.valueOf(8), stack.peek());
        assertEquals(Integer.valueOf(8), stack.peek());
        assertEquals(2, stack.size());
        assertFalse(stack.isEmpty());
        assertEquals(Integer.valueOf(8), stack.pop());
        assertEquals(Integer.valueOf(4), stack.pop());
    }

    @Test
    void popFromEmptyStackThrows() {
        Stack<Integer> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::pop);
    }

    @Test
    void peekFromEmptyStackThrows() {
        Stack<String> stack = new Stack<>();
        assertThrows(NoSuchElementException.class, stack::peek);
    }

    @Test
    void pushNullThrowsWithoutChangingSize() {
        Stack<String> stack = new Stack<>();
        assertThrows(NullPointerException.class, () -> stack.push(null));
        assertTrue(stack.isEmpty());
    }

    @Test
    void stackAlsoWorksWithOwnModel() {
        Stack<DataSample> stack = new Stack<>();
        DataSample sample = new DataSample("S-1", "cat", "camera", 12, SampleStatus.READY);
        stack.push(sample);
        assertEquals(sample, stack.peek());
        assertEquals(sample, stack.pop());
    }
}
