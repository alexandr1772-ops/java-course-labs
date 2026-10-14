package edu.course.lab01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseToolkitTest {

    @Test
    void returnsTrueForEvenNumber() {
        boolean result = CourseToolkit.isEven(8);

        assertTrue(result);
    }

    @Test
    void returnsFalseForOddNumber() {
        boolean result = CourseToolkit.isEven(7);

        assertFalse(result);
    }
    @Test
    void returnsTrueForNegativeEvenNumber() {
        boolean result = CourseToolkit.isEven(-8);

        assertTrue(result);
    }
    @Test
    void returnsTrueForTwo() {
        boolean result = CourseToolkit.isPrime(2);

        assertTrue(result);
    }

    @Test
    void returnsTrueForPrimeNumber() {
        boolean result = CourseToolkit.isPrime(7);

        assertTrue(result);
    }

    @Test
    void returnsFalseForCompositeNumber() {
        boolean result = CourseToolkit.isPrime(9);

        assertFalse(result);
    }

    @Test
    void returnsFalseForPrimeSquare() {
        boolean result = CourseToolkit.isPrime(49);

        assertFalse(result);
    }
    @Test
    void returnsFalseForOne() {
        boolean result = CourseToolkit.isPrime(1);

        assertFalse(result);
    }

    @Test
    void returnsFalseForNegativeNumber() {
        boolean result = CourseToolkit.isPrime(-5);

        assertFalse(result);
    }
    @Test
    void returnsTrueForPalindrome() {
        boolean result = CourseToolkit.isPalindrome("level");

        assertTrue(result);
    }

    @Test
    void returnsFalseForNormalWord() {
        boolean result = CourseToolkit.isPalindrome("hello");

        assertFalse(result);
    }

    @Test
    void returnsFalseForDifferentCase() {
        boolean result = CourseToolkit.isPalindrome("Level");

        assertFalse(result);
    }

    @Test
    void returnsFalseForExtraSpace() {
        boolean result = CourseToolkit.isPalindrome("radar ");

        assertFalse(result);
    }
    @Test
    void returnsTrueForEmptyString() {
        boolean result = CourseToolkit.isPalindrome("");

        assertTrue(result);
    }

    @Test
    void throwsExceptionForNull() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.isPalindrome(null));
    }
    @Test
    void returnsAverageForNumbers() {
        int[] numbers = {2, 4, 6};

        double result = CourseToolkit.average(numbers);

        assertEquals(4.0, result);
    }

    @Test
    void returnsFractionalAverage() {
        int[] numbers = {2, 3};

        double result = CourseToolkit.average(numbers);

        assertEquals(2.5, result);
    }

    @Test
    void returnsAverageForNegativeNumbers() {
        int[] numbers = {-2, -4, -6};

        double result = CourseToolkit.average(numbers);

        assertEquals(-4.0, result);
    }
    @Test
    void throwsExceptionForNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(null));
    }

    @Test
    void throwsExceptionForEmptyArray() {
        int[] numbers = {};

        assertThrows(IllegalArgumentException.class,
                () -> CourseToolkit.average(numbers));
    }

    @Test
    void doesNotChangeOriginalArray() {
        int[] numbers = {2, 4, 6};

        CourseToolkit.average(numbers);

        assertEquals(2, numbers[0]);
        assertEquals(4, numbers[1]);
        assertEquals(6, numbers[2]);
    }
 }
