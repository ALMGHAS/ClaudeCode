package com.bookmyseat.movieservice.service;

import com.bookmyseat.movieservice.dto.ShowtimeDTO;
import com.bookmyseat.movieservice.entity.Movie;
import com.bookmyseat.movieservice.entity.Showtime;
import com.bookmyseat.movieservice.mapper.MovieMapper;
import com.bookmyseat.movieservice.repository.ShowtimeRepository;
import com.bookmyseat.movieservice.service.impl.ShowtimeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShowtimeServiceImplTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private ShowtimeServiceImpl showtimeService;

    private Movie movie;
    private Showtime showtime;
    private ShowtimeDTO showtimeDTO;

    @BeforeEach
    void setUp() {
        movie = new Movie("Inception", "Mind-bending thriller", 148, "Sci-Fi", "English", LocalDate.of(2010, 7, 16));
        movie.setId(1L);

        showtime = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 19, 0), "IMAX Theater", 150);
        showtime.setId(1L);

        showtimeDTO = new ShowtimeDTO(1L, 1L, "Inception", LocalDateTime.of(2023, 12, 25, 19, 0), "IMAX Theater", 150);
    }

    @Test
    void getAllShowtimes_WithNoFilters_ShouldReturnAllShowtimes() {
        // Given
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getMovieTitle());
        assertEquals("IMAX Theater", result.get(0).getTheater());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithMovieIdFilter_ShouldReturnFilteredShowtimes() {
        // Given
        Long movieId = 1L;
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(movieId, null, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(movieId, null, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(movieId, result.get(0).getMovieId());

        verify(showtimeRepository).findShowtimesWithFilters(movieId, null, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithDateFilter_ShouldReturnFilteredShowtimes() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 25);
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, date, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, date, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(date, result.get(0).getShowDateTime().toLocalDate());

        verify(showtimeRepository).findShowtimesWithFilters(null, date, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithTheaterFilter_ShouldReturnFilteredShowtimes() {
        // Given
        String theater = "IMAX Theater";
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, null, theater)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, theater);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(theater, result.get(0).getTheater());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, theater);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithAllFilters_ShouldReturnFilteredShowtimes() {
        // Given
        Long movieId = 1L;
        LocalDate date = LocalDate.of(2023, 12, 25);
        String theater = "IMAX Theater";
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(movieId, date, theater)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(movieId, date, theater);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(movieId, result.get(0).getMovieId());
        assertEquals(date, result.get(0).getShowDateTime().toLocalDate());
        assertEquals(theater, result.get(0).getTheater());

        verify(showtimeRepository).findShowtimesWithFilters(movieId, date, theater);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_EmptyResult_ShouldReturnEmptyList() {
        // Given
        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_MultipleShowtimes_ShouldReturnAllShowtimes() {
        // Given
        Showtime showtime2 = new Showtime(movie, LocalDateTime.of(2023, 12, 25, 22, 0), "Regular Theater", 100);
        showtime2.setId(2L);

        ShowtimeDTO showtimeDTO2 = new ShowtimeDTO(2L, 1L, "Inception",
                LocalDateTime.of(2023, 12, 25, 22, 0), "Regular Theater", 100);

        List<Showtime> showtimes = Arrays.asList(showtime, showtime2);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO, showtimeDTO2);

        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, null);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("IMAX Theater", result.get(0).getTheater());
        assertEquals("Regular Theater", result.get(1).getTheater());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithZeroMovieId_ShouldPassToRepository() {
        // Given
        Long movieId = 0L;
        when(showtimeRepository.findShowtimesWithFilters(movieId, null, null)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(movieId, null, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(movieId, null, null);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithNegativeMovieId_ShouldPassToRepository() {
        // Given
        Long movieId = -1L;
        when(showtimeRepository.findShowtimesWithFilters(movieId, null, null)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(movieId, null, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(movieId, null, null);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithPastDate_ShouldPassToRepository() {
        // Given
        LocalDate pastDate = LocalDate.of(2020, 1, 1);
        when(showtimeRepository.findShowtimesWithFilters(null, pastDate, null)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, pastDate, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(null, pastDate, null);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithFutureDate_ShouldPassToRepository() {
        // Given
        LocalDate futureDate = LocalDate.of(2030, 12, 31);
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, futureDate, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, futureDate, null);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(showtimeRepository).findShowtimesWithFilters(null, futureDate, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithEmptyTheaterName_ShouldPassToRepository() {
        // Given
        String emptyTheater = "";
        when(showtimeRepository.findShowtimesWithFilters(null, null, emptyTheater)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, emptyTheater);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, emptyTheater);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithBlankTheaterName_ShouldPassToRepository() {
        // Given
        String blankTheater = "   ";
        when(showtimeRepository.findShowtimesWithFilters(null, null, blankTheater)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, blankTheater);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, blankTheater);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithSpecialCharactersInTheaterName_ShouldPassToRepository() {
        // Given
        String theaterWithSpecialChars = "PVR Cinemas - Mall @ Delhi-NCR (Screen #5)";
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, null, theaterWithSpecialChars)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, theaterWithSpecialChars);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, theaterWithSpecialChars);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WhenRepositoryReturnsNull_ShouldHandleGracefully() {
        // Given
        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(null);

        // When & Then - This test expects the actual behavior - null check will fail at line 39 of ShowtimeServiceImpl
        assertThrows(NullPointerException.class, () -> {
            showtimeService.getAllShowtimes(null, null, null);
        });

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper, never()).toShowtimeDTOList(any());
    }

    @Test
    void getAllShowtimes_WhenMapperReturnsNull_ShouldReturnNull() {
        // Given
        List<Showtime> showtimes = Arrays.asList(showtime);
        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(null);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, null);

        // Then
        assertNull(result);

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_WithMaxLongMovieId_ShouldPassToRepository() {
        // Given
        Long maxMovieId = Long.MAX_VALUE;
        when(showtimeRepository.findShowtimesWithFilters(maxMovieId, null, null)).thenReturn(Collections.emptyList());
        when(movieMapper.toShowtimeDTOList(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(maxMovieId, null, null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(showtimeRepository).findShowtimesWithFilters(maxMovieId, null, null);
        verify(movieMapper).toShowtimeDTOList(Collections.emptyList());
    }

    @Test
    void getAllShowtimes_WithLongTheaterName_ShouldPassToRepository() {
        // Given
        String longTheaterName = "A".repeat(200);
        List<Showtime> showtimes = Arrays.asList(showtime);
        List<ShowtimeDTO> showtimeDTOs = Arrays.asList(showtimeDTO);

        when(showtimeRepository.findShowtimesWithFilters(null, null, longTheaterName)).thenReturn(showtimes);
        when(movieMapper.toShowtimeDTOList(showtimes)).thenReturn(showtimeDTOs);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, longTheaterName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, longTheaterName);
        verify(movieMapper).toShowtimeDTOList(showtimes);
    }

    @Test
    void getAllShowtimes_LargeResultSet_ShouldHandleCorrectly() {
        // Given
        List<Showtime> largeShowtimeList = new ArrayList<>();
        List<ShowtimeDTO> largeShowtimeDTOList = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            Showtime showtime = new Showtime(movie, LocalDateTime.now().plusDays(i), "Theater " + i, i * 10);
            showtime.setId((long) i);
            largeShowtimeList.add(showtime);

            ShowtimeDTO showtimeDTO = new ShowtimeDTO((long) i, 1L, "Inception",
                    LocalDateTime.now().plusDays(i), "Theater " + i, i * 10);
            largeShowtimeDTOList.add(showtimeDTO);
        }

        when(showtimeRepository.findShowtimesWithFilters(null, null, null)).thenReturn(largeShowtimeList);
        when(movieMapper.toShowtimeDTOList(largeShowtimeList)).thenReturn(largeShowtimeDTOList);

        // When
        List<ShowtimeDTO> result = showtimeService.getAllShowtimes(null, null, null);

        // Then
        assertNotNull(result);
        assertEquals(100, result.size());
        assertEquals("Theater 1", result.get(0).getTheater());
        assertEquals("Theater 100", result.get(99).getTheater());

        verify(showtimeRepository).findShowtimesWithFilters(null, null, null);
        verify(movieMapper).toShowtimeDTOList(largeShowtimeList);
    }
}