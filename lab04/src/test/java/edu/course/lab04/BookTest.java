package edu.course.lab04;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void equalIsbnWorksAsMapKey() {
        Book first = new Book("978-1", "First title");
        Book second = new Book("978-1", "Other title");
        Map<Book, String> books = new HashMap<>();
        books.put(first, "stored");
        assertEquals("stored", books.get(second));
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void differentIsbnIsNotEqual() {
        assertNotEquals(new Book("1", "Same"), new Book("2", "Same"));
    }

    @Test
    void equalityRejectsNullAndOtherType() {
        Book book = new Book("1", "Java");
        assertNotEquals(null, book);
        assertNotEquals("1", book);
        assertEquals(book, book);
    }

    @Test
    void validatesConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Book(null, "Title"));
        assertThrows(IllegalArgumentException.class, () -> new Book(" ", "Title"));
        assertThrows(IllegalArgumentException.class, () -> new Book("123", ""));
    }
}
