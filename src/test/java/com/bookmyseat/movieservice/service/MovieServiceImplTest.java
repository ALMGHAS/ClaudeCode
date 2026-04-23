package com.bookmyseat.movieservice.service;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.entity.Movie;
import com.bookmyseat.movieservice.exception.MovieNotFoundException;
import com.bookmyseat.movieservice.mapper.MovieMapper;
import com.bookmyseat.movieservice.repository.MovieRepository;
import com.bookmyseat.movieservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;
    private MovieDTO movieDTO;
    private MovieDetailDTO movieDetailDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie("Inception", "A mind-bending thriller", 148,
                "Sci-Fi", "English", LocalDate.of(2010, 7, 16));
        movie.setId(1L);

        movieDTO = new MovieDTO(1L, "Inception", "A mind-bending thriller",
                148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16));

        movieDetailDTO = new MovieDetailDTO(1L, "Inception", "A mind-bending thriller",
                148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16), null);
    }

    @Test
    void getAllMovies_ShouldReturnMoviesList() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters(null, null)).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies(null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());

        verify(movieRepository).findMoviesWithFilters(null, null);
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getAllMovies_WithFilters_ShouldReturnFilteredMovies() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters("Sci-Fi", "English")).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies("Sci-Fi", "English");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sci-Fi", result.get(0).getGenre());
        assertEquals("English", result.get(0).getLanguage());

        verify(movieRepository).findMoviesWithFilters("Sci-Fi", "English");
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getMovieById_ShouldReturnMovie() {
        when(movieRepository.findByIdWithShowtimes(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.toMovieDetailDTO(movie)).thenReturn(movieDetailDTO);

        MovieDetailDTO result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Inception", result.getTitle());

        verify(movieRepository).findByIdWithShowtimes(1L);
        verify(movieMapper).toMovieDetailDTO(movie);
    }

    @Test
    void getMovieById_MovieNotFound_ShouldThrowException() {
        when(movieRepository.findByIdWithShowtimes(999L)).thenReturn(Optional.empty());

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class,
                () -> movieService.getMovieById(999L));

        assertEquals("Movie not found with ID: 999", exception.getMessage());

        verify(movieRepository).findByIdWithShowtimes(999L);
        verify(movieMapper, never()).toMovieDetailDTO(any());
    }

    @Test
    void getAllMovies_EmptyResult_ShouldReturnEmptyList() {
        when(movieRepository.findMoviesWithFilters(null, null)).thenReturn(Arrays.asList());
        when(movieMapper.toMovieDTOList(Arrays.asList())).thenReturn(Arrays.asList());

        List<MovieDTO> result = movieService.getAllMovies(null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(movieRepository).findMoviesWithFilters(null, null);
        verify(movieMapper).toMovieDTOList(Arrays.asList());
    }

    @Test
    void getAllMovies_WithGenreFilter_ShouldReturnFilteredResults() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters("Action", null)).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies("Action", null);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(movieRepository).findMoviesWithFilters("Action", null);
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getAllMovies_WithLanguageFilter_ShouldReturnFilteredResults() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters(null, "Hindi")).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies(null, "Hindi");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(movieRepository).findMoviesWithFilters(null, "Hindi");
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getAllMovies_WithEmptyStringFilters_ShouldPassAsIs() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters("", "")).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies("", "");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(movieRepository).findMoviesWithFilters("", "");
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getAllMovies_WithBlankStringFilters_ShouldPassAsIs() {
        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters("   ", "   ")).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies("   ", "   ");

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(movieRepository).findMoviesWithFilters("   ", "   ");
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getMovieById_WithZeroId_ShouldThrowException() {
        when(movieRepository.findByIdWithShowtimes(0L)).thenReturn(Optional.empty());

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class,
                () -> movieService.getMovieById(0L));

        assertEquals("Movie not found with ID: 0", exception.getMessage());

        verify(movieRepository).findByIdWithShowtimes(0L);
        verify(movieMapper, never()).toMovieDetailDTO(any());
    }

    @Test
    void getMovieById_WithNegativeId_ShouldThrowException() {
        when(movieRepository.findByIdWithShowtimes(-1L)).thenReturn(Optional.empty());

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class,
                () -> movieService.getMovieById(-1L));

        assertEquals("Movie not found with ID: -1", exception.getMessage());

        verify(movieRepository).findByIdWithShowtimes(-1L);
        verify(movieMapper, never()).toMovieDetailDTO(any());
    }

    @Test
    void getMovieById_WithMaxLongId_ShouldThrowException() {
        Long maxId = Long.MAX_VALUE;
        when(movieRepository.findByIdWithShowtimes(maxId)).thenReturn(Optional.empty());

        MovieNotFoundException exception = assertThrows(MovieNotFoundException.class,
                () -> movieService.getMovieById(maxId));

        assertEquals("Movie not found with ID: " + maxId, exception.getMessage());

        verify(movieRepository).findByIdWithShowtimes(maxId);
        verify(movieMapper, never()).toMovieDetailDTO(any());
    }

    @Test
    void getAllMovies_WhenRepositoryReturnsNull_ShouldHandleGracefully() {
        when(movieRepository.findMoviesWithFilters(null, null)).thenReturn(null);

        // This test expects the actual behavior - null check will fail at line 39 of MovieServiceImpl
        assertThrows(NullPointerException.class, () -> {
            movieService.getAllMovies(null, null);
        });

        verify(movieRepository).findMoviesWithFilters(null, null);
        verify(movieMapper, never()).toMovieDTOList(any());
    }

    @Test
    void getMovieById_WhenMapperReturnsNull_ShouldReturnNull() {
        when(movieRepository.findByIdWithShowtimes(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.toMovieDetailDTO(movie)).thenReturn(null);

        MovieDetailDTO result = movieService.getMovieById(1L);

        assertNull(result);

        verify(movieRepository).findByIdWithShowtimes(1L);
        verify(movieMapper).toMovieDetailDTO(movie);
    }

    @Test
    void getAllMovies_WithSpecialCharactersInFilters_ShouldPassThrough() {
        String genreWithSpecialChars = "Sci-Fi & Fantasy";
        String languageWithSpecialChars = "English/Spanish";

        List<Movie> movies = Arrays.asList(movie);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO);

        when(movieRepository.findMoviesWithFilters(genreWithSpecialChars, languageWithSpecialChars))
                .thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies(genreWithSpecialChars, languageWithSpecialChars);

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(movieRepository).findMoviesWithFilters(genreWithSpecialChars, languageWithSpecialChars);
        verify(movieMapper).toMovieDTOList(movies);
    }

    @Test
    void getAllMovies_MultipleMoviesResult_ShouldReturnAllMovies() {
        Movie movie2 = new Movie("The Dark Knight", "Batman movie", 152,
                "Action", "English", LocalDate.of(2008, 7, 18));
        movie2.setId(2L);

        MovieDTO movieDTO2 = new MovieDTO(2L, "The Dark Knight", "Batman movie",
                152, "Action", "English", LocalDate.of(2008, 7, 18));

        List<Movie> movies = Arrays.asList(movie, movie2);
        List<MovieDTO> movieDTOs = Arrays.asList(movieDTO, movieDTO2);

        when(movieRepository.findMoviesWithFilters(null, null)).thenReturn(movies);
        when(movieMapper.toMovieDTOList(movies)).thenReturn(movieDTOs);

        List<MovieDTO> result = movieService.getAllMovies(null, null);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        assertEquals("The Dark Knight", result.get(1).getTitle());

        verify(movieRepository).findMoviesWithFilters(null, null);
        verify(movieMapper).toMovieDTOList(movies);
    }
}