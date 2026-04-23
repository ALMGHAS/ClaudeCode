package com.bookmyseat.movieservice.service;

import com.bookmyseat.movieservice.dto.ShowtimeDTO;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {

    List<ShowtimeDTO> getAllShowtimes(Long movieId, LocalDate date, String theater);
}