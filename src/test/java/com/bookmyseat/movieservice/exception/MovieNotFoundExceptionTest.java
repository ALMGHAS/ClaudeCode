package com.bookmyseat.movieservice.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieNotFoundExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Movie with id 1 not found";
        MovieNotFoundException exception = new MovieNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Movie with id 1 not found";
        Throwable cause = new IllegalArgumentException("Invalid ID");

        MovieNotFoundException exception = new MovieNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testConstructorWithNullMessage() {
        MovieNotFoundException exception = new MovieNotFoundException(null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        String emptyMessage = "";
        MovieNotFoundException exception = new MovieNotFoundException(emptyMessage);

        assertEquals(emptyMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithNullMessageAndCause() {
        Throwable cause = new IllegalArgumentException("Test cause");
        MovieNotFoundException exception = new MovieNotFoundException(null, cause);

        assertNull(exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndNullCause() {
        String message = "Test message";
        MovieNotFoundException exception = new MovieNotFoundException(message, null);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testExceptionInheritance() {
        MovieNotFoundException exception = new MovieNotFoundException("Test");

        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void testExceptionCanBeThrown() {
        assertThrows(MovieNotFoundException.class, () -> {
            throw new MovieNotFoundException("Movie not found");
        });
    }

    @Test
    void testExceptionCanBeCaught() {
        try {
            throw new MovieNotFoundException("Movie with id 999 not found");
        } catch (MovieNotFoundException e) {
            assertEquals("Movie with id 999 not found", e.getMessage());
        }
    }

    @Test
    void testExceptionWithLongMessage() {
        String longMessage = "A".repeat(1000);
        MovieNotFoundException exception = new MovieNotFoundException(longMessage);

        assertEquals(longMessage, exception.getMessage());
        assertEquals(1000, exception.getMessage().length());
    }

    @Test
    void testExceptionWithSpecialCharacters() {
        String messageWithSpecialChars = "Movie not found: ID=123, Genre='Sci-Fi', Title=\"The Matrix\"";
        MovieNotFoundException exception = new MovieNotFoundException(messageWithSpecialChars);

        assertEquals(messageWithSpecialChars, exception.getMessage());
    }

    @Test
    void testExceptionStackTrace() {
        MovieNotFoundException exception = new MovieNotFoundException("Test exception");
        StackTraceElement[] stackTrace = exception.getStackTrace();

        assertNotNull(stackTrace);
        assertTrue(stackTrace.length > 0);
    }

    @Test
    void testNestedExceptionCause() {
        RuntimeException rootCause = new RuntimeException("Root cause");
        IllegalArgumentException intermediateCause = new IllegalArgumentException("Intermediate cause", rootCause);
        MovieNotFoundException exception = new MovieNotFoundException("Movie not found", intermediateCause);

        assertEquals("Movie not found", exception.getMessage());
        assertEquals(intermediateCause, exception.getCause());
        assertEquals(rootCause, exception.getCause().getCause());
    }
}