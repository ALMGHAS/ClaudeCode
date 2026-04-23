package com.bookmyseat.movieservice.controller;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.dto.ShowtimeDTO;
import com.bookmyseat.movieservice.exception.MovieNotFoundException;
import com.bookmyseat.movieservice.service.MovieService;
import com.bookmyseat.movieservice.service.ShowtimeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private ShowtimeService showtimeService;

    @Autowired
    private ObjectMapper objectMapper;

    private MovieDTO movieDTO;
    private MovieDetailDTO movieDetailDTO;
    private ShowtimeDTO showtimeDTO;

    @BeforeEach
    void setUp() {
        movieDTO = new MovieDTO(1L, "Inception", "A mind-bending thriller",
                148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16));

        showtimeDTO = new ShowtimeDTO(1L, 1L, "Inception",
                LocalDateTime.of(2025, 9, 30, 14, 0),
                "Theater 1", 100);

        movieDetailDTO = new MovieDetailDTO(1L, "Inception", "A mind-bending thriller",
                148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16),
                Arrays.asList(showtimeDTO));
    }

    @Test
    void getAllMovies_ShouldReturnMoviesList() throws Exception {
        List<MovieDTO> movies = Arrays.asList(movieDTO);
        when(movieService.getAllMovies(null, null)).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.movies").exists())
                .andExpect(jsonPath("$.movies").isArray())
                .andExpect(jsonPath("$.movies[0].id").value(1))
                .andExpect(jsonPath("$.movies[0].title").value("Inception"));
    }

    @Test
    void getAllMovies_WithFilters_ShouldReturnFilteredMovies() throws Exception {
        List<MovieDTO> movies = Arrays.asList(movieDTO);
        when(movieService.getAllMovies("Sci-Fi", "English")).thenReturn(movies);

        mockMvc.perform(get("/api/v1/movies")
                        .param("genre", "Sci-Fi")
                        .param("language", "English"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.movies[0].genre").value("Sci-Fi"))
                .andExpect(jsonPath("$.movies[0].language").value("English"));
    }

    @Test
    void getMovieById_ShouldReturnMovie() throws Exception {
        when(movieService.getMovieById(1L)).thenReturn(movieDetailDTO);

        mockMvc.perform(get("/api/v1/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.showtimes").exists())
                .andExpect(jsonPath("$.showtimes").isArray());
    }

    @Test
    void getMovieById_MovieNotFound_ShouldReturn404() throws Exception {
        when(movieService.getMovieById(999L)).thenThrow(new MovieNotFoundException("Movie not found with ID: 999"));

        mockMvc.perform(get("/api/v1/movies/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Movie not found with ID: 999"));
    }

    @Test
    void getAllShowtimes_ShouldReturnShowtimesList() throws Exception {
        List<ShowtimeDTO> showtimes = Arrays.asList(showtimeDTO);
        when(showtimeService.getAllShowtimes(null, null, null)).thenReturn(showtimes);

        mockMvc.perform(get("/api/v1/showtimes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.showtimes").exists())
                .andExpect(jsonPath("$.showtimes").isArray())
                .andExpect(jsonPath("$.showtimes[0].id").value(1))
                .andExpect(jsonPath("$.showtimes[0].movieTitle").value("Inception"));
    }

    @Test
    void getAllShowtimes_WithFilters_ShouldReturnFilteredShowtimes() throws Exception {
        List<ShowtimeDTO> showtimes = Arrays.asList(showtimeDTO);
        when(showtimeService.getAllShowtimes(eq(1L), any(LocalDate.class), eq("Theater 1")))
                .thenReturn(showtimes);

        mockMvc.perform(get("/api/v1/showtimes")
                        .param("movieId", "1")
                        .param("date", "2025-09-30")
                        .param("theater", "Theater 1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.showtimes[0].movieId").value(1))
                .andExpect(jsonPath("$.showtimes[0].theater").value("Theater 1"));
    }
}
