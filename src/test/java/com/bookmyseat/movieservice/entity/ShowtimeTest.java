package com.bookmyseat.movieservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowtimeTest {

    @Test
    void testDefaultConstructor() {
        Showtime showtime = new Showtime();
        assertNotNull(showtime);
        assertNull(showtime.getId());
        assertNull(showtime.getMovie());
        assertNull(showtime.getShowDateTime());
        assertNull(showtime.getTheater());
        assertNull(showtime.getAvailableSeats());
        assertNull(showtime.getCreatedAt());
        assertNull(showtime.getUpdatedAt());
    }

    @Test
    void testParameterizedConstructor() {
        Movie movie = new Movie("Test Movie", "Description", 120, "Action", "English", LocalDate.now());
        LocalDateTime showDateTime = LocalDateTime.now().plusDays(1);
        String theater = "Theater A";
        Integer availableSeats = 100;

        Showtime showtime = new Showtime(movie, showDateTime, theater, availableSeats);

        assertEquals(movie, showtime.getMovie());
        assertEquals(showDateTime, showtime.getShowDateTime());
        assertEquals(theater, showtime.getTheater());
        assertEquals(availableSeats, showtime.getAvailableSeats());
        assertNull(showtime.getId()); // ID should be null until persisted
    }

    @Test
    void testGettersAndSetters() {
        Showtime showtime = new Showtime();

        // Test ID
        Long id = 1L;
        showtime.setId(id);
        assertEquals(id, showtime.getId());

        // Test movie
        Movie movie = new Movie("Inception", "Mind-bending thriller", 148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16));
        showtime.setMovie(movie);
        assertEquals(movie, showtime.getMovie());

        // Test show date time
        LocalDateTime showDateTime = LocalDateTime.of(2023, 12, 25, 19, 30);
        showtime.setShowDateTime(showDateTime);
        assertEquals(showDateTime, showtime.getShowDateTime());

        // Test theater
        String theater = "IMAX Theater";
        showtime.setTheater(theater);
        assertEquals(theater, showtime.getTheater());

        // Test available seats
        Integer availableSeats = 150;
        showtime.setAvailableSeats(availableSeats);
        assertEquals(availableSeats, showtime.getAvailableSeats());

        // Test created at
        LocalDateTime createdAt = LocalDateTime.now();
        showtime.setCreatedAt(createdAt);
        assertEquals(createdAt, showtime.getCreatedAt());

        // Test updated at
        LocalDateTime updatedAt = LocalDateTime.now();
        showtime.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, showtime.getUpdatedAt());
    }

    @Test
    void testNullValues() {
        Showtime showtime = new Showtime();

        // Test setting null values
        showtime.setId(null);
        showtime.setMovie(null);
        showtime.setShowDateTime(null);
        showtime.setTheater(null);
        showtime.setAvailableSeats(null);
        showtime.setCreatedAt(null);
        showtime.setUpdatedAt(null);

        assertNull(showtime.getId());
        assertNull(showtime.getMovie());
        assertNull(showtime.getShowDateTime());
        assertNull(showtime.getTheater());
        assertNull(showtime.getAvailableSeats());
        assertNull(showtime.getCreatedAt());
        assertNull(showtime.getUpdatedAt());
    }

    @Test
    void testZeroAvailableSeats() {
        Showtime showtime = new Showtime();
        showtime.setAvailableSeats(0);
        assertEquals(0, showtime.getAvailableSeats());
    }

    @Test
    void testNegativeAvailableSeats() {
        Showtime showtime = new Showtime();
        showtime.setAvailableSeats(-5);
        assertEquals(-5, showtime.getAvailableSeats());
    }

    @Test
    void testLargeAvailableSeats() {
        Showtime showtime = new Showtime();
        Integer largeNumber = 999999;
        showtime.setAvailableSeats(largeNumber);
        assertEquals(largeNumber, showtime.getAvailableSeats());
    }

    @Test
    void testPastShowDateTime() {
        Showtime showtime = new Showtime();
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        showtime.setShowDateTime(pastDateTime);
        assertEquals(pastDateTime, showtime.getShowDateTime());
    }

    @Test
    void testFutureShowDateTime() {
        Showtime showtime = new Showtime();
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(30);
        showtime.setShowDateTime(futureDateTime);
        assertEquals(futureDateTime, showtime.getShowDateTime());
    }

    @Test
    void testTheaterNameWithSpecialCharacters() {
        Showtime showtime = new Showtime();
        String theaterWithSpecialChars = "PVR Cinemas - Mall @ Delhi-NCR (Screen #5)";
        showtime.setTheater(theaterWithSpecialChars);
        assertEquals(theaterWithSpecialChars, showtime.getTheater());
    }

    @Test
    void testLongTheaterName() {
        Showtime showtime = new Showtime();
        String longTheaterName = "A".repeat(100);
        showtime.setTheater(longTheaterName);
        assertEquals(longTheaterName, showtime.getTheater());
    }

    @Test
    void testEmptyTheaterName() {
        Showtime showtime = new Showtime();
        String emptyTheater = "";
        showtime.setTheater(emptyTheater);
        assertEquals(emptyTheater, showtime.getTheater());
    }

    @Test
    void testShowtimeTimestamps() {
        Showtime showtime = new Showtime();
        LocalDateTime now = LocalDateTime.now();

        showtime.setCreatedAt(now);
        showtime.setUpdatedAt(now.plusMinutes(10));

        assertEquals(now, showtime.getCreatedAt());
        assertEquals(now.plusMinutes(10), showtime.getUpdatedAt());

        // Test that updatedAt is after createdAt
        assertTrue(showtime.getUpdatedAt().isAfter(showtime.getCreatedAt()));
    }

    @Test
    void testShowtimeWithCompleteMovieDetails() {
        Movie movie = new Movie("The Dark Knight", "Batman fights Joker", 152, "Action", "English", LocalDate.of(2008, 7, 18));
        movie.setId(1L);

        LocalDateTime showDateTime = LocalDateTime.of(2023, 12, 31, 21, 0);
        String theater = "IMAX Theater Downtown";
        Integer availableSeats = 250;

        Showtime showtime = new Showtime(movie, showDateTime, theater, availableSeats);

        // Verify all relationships
        assertEquals(movie, showtime.getMovie());
        assertEquals("The Dark Knight", showtime.getMovie().getTitle());
        assertEquals(1L, showtime.getMovie().getId());
        assertEquals(showDateTime, showtime.getShowDateTime());
        assertEquals(theater, showtime.getTheater());
        assertEquals(availableSeats, showtime.getAvailableSeats());
    }

    @Test
    void testShowtimeDateTimeComparison() {
        Showtime showtime1 = new Showtime();
        Showtime showtime2 = new Showtime();

        LocalDateTime baseTime = LocalDateTime.of(2023, 12, 25, 18, 0);
        LocalDateTime laterTime = baseTime.plusHours(2);

        showtime1.setShowDateTime(baseTime);
        showtime2.setShowDateTime(laterTime);

        assertTrue(showtime2.getShowDateTime().isAfter(showtime1.getShowDateTime()));
        assertTrue(showtime1.getShowDateTime().isBefore(showtime2.getShowDateTime()));
    }
}