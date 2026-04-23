package com.bookmyseat.movieservice.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void testDefaultConstructor() {
        Movie movie = new Movie();
        assertNotNull(movie);
        assertNull(movie.getId());
        assertNull(movie.getTitle());
        assertNull(movie.getDescription());
        assertNull(movie.getDurationMinutes());
        assertNull(movie.getGenre());
        assertNull(movie.getLanguage());
        assertNull(movie.getReleaseDate());
        assertNull(movie.getCreatedAt());
        assertNull(movie.getUpdatedAt());
        assertNull(movie.getShowtimes());
    }

    @Test
    void testParameterizedConstructor() {
        String title = "Test Movie";
        String description = "Test Description";
        Integer duration = 120;
        String genre = "Action";
        String language = "English";
        LocalDate releaseDate = LocalDate.of(2023, 1, 1);

        Movie movie = new Movie(title, description, duration, genre, language, releaseDate);

        assertEquals(title, movie.getTitle());
        assertEquals(description, movie.getDescription());
        assertEquals(duration, movie.getDurationMinutes());
        assertEquals(genre, movie.getGenre());
        assertEquals(language, movie.getLanguage());
        assertEquals(releaseDate, movie.getReleaseDate());
        assertNull(movie.getId()); // ID should be null until persisted
    }

    @Test
    void testGettersAndSetters() {
        Movie movie = new Movie();

        // Test ID
        Long id = 1L;
        movie.setId(id);
        assertEquals(id, movie.getId());

        // Test title
        String title = "Inception";
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());

        // Test description
        String description = "A mind-bending thriller";
        movie.setDescription(description);
        assertEquals(description, movie.getDescription());

        // Test duration
        Integer duration = 148;
        movie.setDurationMinutes(duration);
        assertEquals(duration, movie.getDurationMinutes());

        // Test genre
        String genre = "Sci-Fi";
        movie.setGenre(genre);
        assertEquals(genre, movie.getGenre());

        // Test language
        String language = "English";
        movie.setLanguage(language);
        assertEquals(language, movie.getLanguage());

        // Test release date
        LocalDate releaseDate = LocalDate.of(2010, 7, 16);
        movie.setReleaseDate(releaseDate);
        assertEquals(releaseDate, movie.getReleaseDate());

        // Test created at
        LocalDateTime createdAt = LocalDateTime.now();
        movie.setCreatedAt(createdAt);
        assertEquals(createdAt, movie.getCreatedAt());

        // Test updated at
        LocalDateTime updatedAt = LocalDateTime.now();
        movie.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, movie.getUpdatedAt());

        // Test showtimes
        List<Showtime> showtimes = new ArrayList<>();
        Showtime showtime = new Showtime();
        showtimes.add(showtime);
        movie.setShowtimes(showtimes);
        assertEquals(showtimes, movie.getShowtimes());
        assertEquals(1, movie.getShowtimes().size());
    }

    @Test
    void testNullValues() {
        Movie movie = new Movie();

        // Test setting null values
        movie.setId(null);
        movie.setTitle(null);
        movie.setDescription(null);
        movie.setDurationMinutes(null);
        movie.setGenre(null);
        movie.setLanguage(null);
        movie.setReleaseDate(null);
        movie.setCreatedAt(null);
        movie.setUpdatedAt(null);
        movie.setShowtimes(null);

        assertNull(movie.getId());
        assertNull(movie.getTitle());
        assertNull(movie.getDescription());
        assertNull(movie.getDurationMinutes());
        assertNull(movie.getGenre());
        assertNull(movie.getLanguage());
        assertNull(movie.getReleaseDate());
        assertNull(movie.getCreatedAt());
        assertNull(movie.getUpdatedAt());
        assertNull(movie.getShowtimes());
    }

    @Test
    void testEmptyShowtimesList() {
        Movie movie = new Movie();
        List<Showtime> emptyShowtimes = new ArrayList<>();
        movie.setShowtimes(emptyShowtimes);

        assertNotNull(movie.getShowtimes());
        assertTrue(movie.getShowtimes().isEmpty());
        assertEquals(0, movie.getShowtimes().size());
    }

    @Test
    void testMovieWithMultipleShowtimes() {
        Movie movie = new Movie("Test Movie", "Description", 120, "Action", "English", LocalDate.now());

        List<Showtime> showtimes = new ArrayList<>();

        // Create multiple showtimes
        Showtime showtime1 = new Showtime(movie, LocalDateTime.now().plusDays(1), "Theater A", 100);
        Showtime showtime2 = new Showtime(movie, LocalDateTime.now().plusDays(2), "Theater B", 150);
        Showtime showtime3 = new Showtime(movie, LocalDateTime.now().plusDays(3), "Theater C", 200);

        showtimes.add(showtime1);
        showtimes.add(showtime2);
        showtimes.add(showtime3);

        movie.setShowtimes(showtimes);

        assertEquals(3, movie.getShowtimes().size());
        assertTrue(movie.getShowtimes().contains(showtime1));
        assertTrue(movie.getShowtimes().contains(showtime2));
        assertTrue(movie.getShowtimes().contains(showtime3));
    }

    @Test
    void testMovieFieldLengths() {
        Movie movie = new Movie();

        // Test very long strings
        String longTitle = "A".repeat(255);
        String longDescription = "B".repeat(1000);
        String longGenre = "C".repeat(100);
        String longLanguage = "D".repeat(50);

        movie.setTitle(longTitle);
        movie.setDescription(longDescription);
        movie.setGenre(longGenre);
        movie.setLanguage(longLanguage);

        assertEquals(longTitle, movie.getTitle());
        assertEquals(longDescription, movie.getDescription());
        assertEquals(longGenre, movie.getGenre());
        assertEquals(longLanguage, movie.getLanguage());
    }

    @Test
    void testMovieWithZeroDuration() {
        Movie movie = new Movie();
        movie.setDurationMinutes(0);
        assertEquals(0, movie.getDurationMinutes());
    }

    @Test
    void testMovieWithNegativeDuration() {
        Movie movie = new Movie();
        movie.setDurationMinutes(-10);
        assertEquals(-10, movie.getDurationMinutes());
    }

    @Test
    void testMovieTimestamps() {
        Movie movie = new Movie();
        LocalDateTime now = LocalDateTime.now();

        movie.setCreatedAt(now);
        movie.setUpdatedAt(now.plusMinutes(5));

        assertEquals(now, movie.getCreatedAt());
        assertEquals(now.plusMinutes(5), movie.getUpdatedAt());

        // Test that updatedAt is after createdAt
        assertTrue(movie.getUpdatedAt().isAfter(movie.getCreatedAt()));
    }
}