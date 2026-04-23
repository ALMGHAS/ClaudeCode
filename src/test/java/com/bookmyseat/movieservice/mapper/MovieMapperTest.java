package com.bookmyseat.movieservice.mapper;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.dto.ShowtimeDTO;
import com.bookmyseat.movieservice.entity.Movie;
import com.bookmyseat.movieservice.entity.Showtime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapper();
    }

    @Test
    void testToMovieDTO_WithValidMovie() {
        // Given
        Movie movie = new Movie("Inception", "Mind-bending thriller", 148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16));
        movie.setId(1L);

        // When
        MovieDTO movieDTO = movieMapper.toMovieDTO(movie);

        // Then
        assertNotNull(movieDTO);
        assertEquals(1L, movieDTO.getId());
        assertEquals("Inception", movieDTO.getTitle());
        assertEquals("Mind-bending thriller", movieDTO.getDescription());
        assertEquals(148, movieDTO.getDurationMinutes());
        assertEquals("Sci-Fi", movieDTO.getGenre());
        assertEquals("English", movieDTO.getLanguage());
        assertEquals(LocalDate.of(2010, 7, 16), movieDTO.getReleaseDate());
    }

    @Test
    void testToMovieDTO_WithNullMovie() {
        // When
        MovieDTO movieDTO = movieMapper.toMovieDTO(null);

        // Then
        assertNull(movieDTO);
    }

    @Test
    void testToMovieDTO_WithMovieHavingNullFields() {
        // Given
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");

        // When
        MovieDTO movieDTO = movieMapper.toMovieDTO(movie);

        // Then
        assertNotNull(movieDTO);
        assertEquals(1L, movieDTO.getId());
        assertEquals("Test Movie", movieDTO.getTitle());
        assertNull(movieDTO.getDescription());
        assertNull(movieDTO.getDurationMinutes());
        assertNull(movieDTO.getGenre());
        assertNull(movieDTO.getLanguage());
        assertNull(movieDTO.getReleaseDate());
    }

    @Test
    void testToMovieDTOList_WithValidMovieList() {
        // Given
        Movie movie1 = new Movie("Movie 1", "Description 1", 120, "Action", "English", LocalDate.of(2023, 1, 1));
        movie1.setId(1L);
        Movie movie2 = new Movie("Movie 2", "Description 2", 90, "Comedy", "Hindi", LocalDate.of(2023, 2, 1));
        movie2.setId(2L);
        List<Movie> movies = Arrays.asList(movie1, movie2);

        // When
        List<MovieDTO> movieDTOs = movieMapper.toMovieDTOList(movies);

        // Then
        assertNotNull(movieDTOs);
        assertEquals(2, movieDTOs.size());

        MovieDTO dto1 = movieDTOs.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Movie 1", dto1.getTitle());
        assertEquals("Action", dto1.getGenre());

        MovieDTO dto2 = movieDTOs.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Movie 2", dto2.getTitle());
        assertEquals("Comedy", dto2.getGenre());
    }

    @Test
    void testToMovieDTOList_WithNullList() {
        // When
        List<MovieDTO> movieDTOs = movieMapper.toMovieDTOList(null);

        // Then
        assertNull(movieDTOs);
    }

    @Test
    void testToMovieDTOList_WithEmptyList() {
        // Given
        List<Movie> emptyList = new ArrayList<>();

        // When
        List<MovieDTO> movieDTOs = movieMapper.toMovieDTOList(emptyList);

        // Then
        assertNotNull(movieDTOs);
        assertTrue(movieDTOs.isEmpty());
    }

    @Test
    void testToMovieDTOList_WithListContainingNullMovie() {
        // Given
        Movie movie = new Movie("Valid Movie", "Description", 120, "Action", "English", LocalDate.now());
        movie.setId(1L);
        List<Movie> movies = Arrays.asList(movie, null);

        // When
        List<MovieDTO> movieDTOs = movieMapper.toMovieDTOList(movies);

        // Then
        assertNotNull(movieDTOs);
        assertEquals(2, movieDTOs.size());
        assertNotNull(movieDTOs.get(0));
        assertNull(movieDTOs.get(1));
    }

    @Test
    void testToMovieDetailDTO_WithValidMovieAndShowtimes() {
        // Given
        Movie movie = new Movie("The Dark Knight", "Batman fights crime", 152, "Action", "English", LocalDate.of(2008, 7, 18));
        movie.setId(1L);

        Showtime showtime1 = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 19, 0), "Theater A", 100);
        showtime1.setId(1L);
        Showtime showtime2 = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 22, 0), "Theater B", 150);
        showtime2.setId(2L);

        movie.setShowtimes(Arrays.asList(showtime1, showtime2));

        // When
        MovieDetailDTO movieDetailDTO = movieMapper.toMovieDetailDTO(movie);

        // Then
        assertNotNull(movieDetailDTO);
        assertEquals(1L, movieDetailDTO.getId());
        assertEquals("The Dark Knight", movieDetailDTO.getTitle());
        assertEquals("Batman fights crime", movieDetailDTO.getDescription());
        assertEquals(152, movieDetailDTO.getDurationMinutes());
        assertEquals("Action", movieDetailDTO.getGenre());
        assertEquals("English", movieDetailDTO.getLanguage());
        assertEquals(LocalDate.of(2008, 7, 18), movieDetailDTO.getReleaseDate());

        assertNotNull(movieDetailDTO.getShowtimes());
        assertEquals(2, movieDetailDTO.getShowtimes().size());

        ShowtimeDTO showtimeDTO1 = movieDetailDTO.getShowtimes().get(0);
        assertEquals(1L, showtimeDTO1.getId());
        assertEquals(1L, showtimeDTO1.getMovieId());
        assertEquals("The Dark Knight", showtimeDTO1.getMovieTitle());
        assertEquals("Theater A", showtimeDTO1.getTheater());
        assertEquals(100, showtimeDTO1.getAvailableSeats());
    }

    @Test
    void testToMovieDetailDTO_WithNullMovie() {
        // When
        MovieDetailDTO movieDetailDTO = movieMapper.toMovieDetailDTO(null);

        // Then
        assertNull(movieDetailDTO);
    }

    @Test
    void testToMovieDetailDTO_WithMovieHavingNullShowtimes() {
        // Given
        Movie movie = new Movie("Test Movie", "Description", 120, "Drama", "English", LocalDate.now());
        movie.setId(1L);
        movie.setShowtimes(null);

        // When
        MovieDetailDTO movieDetailDTO = movieMapper.toMovieDetailDTO(movie);

        // Then
        assertNotNull(movieDetailDTO);
        assertEquals(1L, movieDetailDTO.getId());
        assertEquals("Test Movie", movieDetailDTO.getTitle());
        assertNull(movieDetailDTO.getShowtimes());
    }

    @Test
    void testToMovieDetailDTO_WithMovieHavingEmptyShowtimes() {
        // Given
        Movie movie = new Movie("Test Movie", "Description", 120, "Drama", "English", LocalDate.now());
        movie.setId(1L);
        movie.setShowtimes(new ArrayList<>());

        // When
        MovieDetailDTO movieDetailDTO = movieMapper.toMovieDetailDTO(movie);

        // Then
        assertNotNull(movieDetailDTO);
        assertEquals(1L, movieDetailDTO.getId());
        assertNotNull(movieDetailDTO.getShowtimes());
        assertTrue(movieDetailDTO.getShowtimes().isEmpty());
    }

    @Test
    void testToShowtimeDTO_WithValidShowtime() {
        // Given
        Movie movie = new Movie("Test Movie", "Description", 120, "Action", "English", LocalDate.now());
        movie.setId(1L);

        Showtime showtime = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 20, 0), "IMAX Theater", 200);
        showtime.setId(1L);

        // When
        ShowtimeDTO showtimeDTO = movieMapper.toShowtimeDTO(showtime);

        // Then
        assertNotNull(showtimeDTO);
        assertEquals(1L, showtimeDTO.getId());
        assertEquals(1L, showtimeDTO.getMovieId());
        assertEquals("Test Movie", showtimeDTO.getMovieTitle());
        assertEquals(LocalDateTime.of(2023, 12, 25, 20, 0), showtimeDTO.getShowDateTime());
        assertEquals("IMAX Theater", showtimeDTO.getTheater());
        assertEquals(200, showtimeDTO.getAvailableSeats());
    }

    @Test
    void testToShowtimeDTO_WithNullShowtime() {
        // When
        ShowtimeDTO showtimeDTO = movieMapper.toShowtimeDTO(null);

        // Then
        assertNull(showtimeDTO);
    }

    @Test
    void testToShowtimeDTOList_WithValidShowtimeList() {
        // Given
        Movie movie = new Movie("Test Movie", "Description", 120, "Action", "English", LocalDate.now());
        movie.setId(1L);

        Showtime showtime1 = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 19, 0), "Theater A", 100);
        showtime1.setId(1L);
        Showtime showtime2 = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 22, 0), "Theater B", 150);
        showtime2.setId(2L);

        List<Showtime> showtimes = Arrays.asList(showtime1, showtime2);

        // When
        List<ShowtimeDTO> showtimeDTOs = movieMapper.toShowtimeDTOList(showtimes);

        // Then
        assertNotNull(showtimeDTOs);
        assertEquals(2, showtimeDTOs.size());

        ShowtimeDTO dto1 = showtimeDTOs.get(0);
        assertEquals(1L, dto1.getId());
        assertEquals("Theater A", dto1.getTheater());
        assertEquals(100, dto1.getAvailableSeats());

        ShowtimeDTO dto2 = showtimeDTOs.get(1);
        assertEquals(2L, dto2.getId());
        assertEquals("Theater B", dto2.getTheater());
        assertEquals(150, dto2.getAvailableSeats());
    }

    @Test
    void testToShowtimeDTOList_WithNullList() {
        // When
        List<ShowtimeDTO> showtimeDTOs = movieMapper.toShowtimeDTOList(null);

        // Then
        assertNull(showtimeDTOs);
    }

    @Test
    void testToShowtimeDTOList_WithEmptyList() {
        // Given
        List<Showtime> emptyList = new ArrayList<>();

        // When
        List<ShowtimeDTO> showtimeDTOs = movieMapper.toShowtimeDTOList(emptyList);

        // Then
        assertNotNull(showtimeDTOs);
        assertTrue(showtimeDTOs.isEmpty());
    }

    @Test
    void testToShowtimeDTOList_WithListContainingNullShowtime() {
        // Given
        Movie movie = new Movie("Test Movie", "Description", 120, "Action", "English", LocalDate.now());
        movie.setId(1L);

        Showtime validShowtime = new Showtime(movie, LocalDateTime.now(), "Theater A", 100);
        validShowtime.setId(1L);

        List<Showtime> showtimes = Arrays.asList(validShowtime, null);

        // When
        List<ShowtimeDTO> showtimeDTOs = movieMapper.toShowtimeDTOList(showtimes);

        // Then
        assertNotNull(showtimeDTOs);
        assertEquals(2, showtimeDTOs.size());
        assertNotNull(showtimeDTOs.get(0));
        assertNull(showtimeDTOs.get(1));
    }

    @Test
    void testComplexMappingScenario() {
        // Given
        Movie movie = new Movie("Complex Movie", "Long description", 180, "Drama", "French", LocalDate.of(2020, 5, 15));
        movie.setId(999L);

        List<Showtime> showtimes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Showtime showtime = new Showtime(movie, LocalDateTime.now().plusDays(i), "Theater " + i, i * 50);
            showtime.setId((long) i);
            showtimes.add(showtime);
        }
        movie.setShowtimes(showtimes);

        // When
        MovieDetailDTO detailDTO = movieMapper.toMovieDetailDTO(movie);
        MovieDTO simpleDTO = movieMapper.toMovieDTO(movie);
        List<ShowtimeDTO> showtimeDTOs = movieMapper.toShowtimeDTOList(showtimes);

        // Then
        assertNotNull(detailDTO);
        assertNotNull(simpleDTO);
        assertNotNull(showtimeDTOs);

        assertEquals(5, detailDTO.getShowtimes().size());
        assertEquals(5, showtimeDTOs.size());

        // Verify consistency between different mappings
        assertEquals(simpleDTO.getId(), detailDTO.getId());
        assertEquals(simpleDTO.getTitle(), detailDTO.getTitle());
        assertEquals(simpleDTO.getGenre(), detailDTO.getGenre());
    }

    @Test
    void testMappingWithSpecialCharacters() {
        // Given
        Movie movie = new Movie(
                "Movie: The \"Special\" Edition!",
                "Description with symbols: @#$%^&*()",
                95,
                "Sci-Fi/Action",
                "English/Spanish",
                LocalDate.of(2023, 3, 15)
        );
        movie.setId(123L);

        // When
        MovieDTO movieDTO = movieMapper.toMovieDTO(movie);

        // Then
        assertNotNull(movieDTO);
        assertEquals("Movie: The \"Special\" Edition!", movieDTO.getTitle());
        assertEquals("Description with symbols: @#$%^&*()", movieDTO.getDescription());
        assertEquals("Sci-Fi/Action", movieDTO.getGenre());
        assertEquals("English/Spanish", movieDTO.getLanguage());
    }
}