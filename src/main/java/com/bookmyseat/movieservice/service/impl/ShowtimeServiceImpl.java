package com.bookmyseat.movieservice.service.impl;

import com.bookmyseat.movieservice.dto.ShowtimeDTO;
import com.bookmyseat.movieservice.entity.Showtime;
import com.bookmyseat.movieservice.mapper.MovieMapper;
import com.bookmyseat.movieservice.repository.ShowtimeRepository;
import com.bookmyseat.movieservice.service.ShowtimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ShowtimeServiceImpl implements ShowtimeService {

    private static final Logger logger = LoggerFactory.getLogger(ShowtimeServiceImpl.class);

    private final ShowtimeRepository showtimeRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository, MovieMapper movieMapper) {
        this.showtimeRepository = showtimeRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<ShowtimeDTO> getAllShowtimes(Long movieId, LocalDate date, String theater) {
        logger.info("Fetching showtimes with filters - movieId: {}, date: {}, theater: {}",
                   movieId, date, theater);

        List<Showtime> showtimes = showtimeRepository.findShowtimesWithFilters(movieId, date, theater);

        logger.info("Found {} showtimes", showtimes.size());
        return movieMapper.toShowtimeDTOList(showtimes);
    }
}