package com.bookmyseat.movieservice.service.impl;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.entity.Movie;
import com.bookmyseat.movieservice.exception.MovieNotFoundException;
import com.bookmyseat.movieservice.mapper.MovieMapper;
import com.bookmyseat.movieservice.repository.MovieRepository;
import com.bookmyseat.movieservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> getAllMovies(String genre, String language) {
        logger.info("Fetching movies with filters - genre: {}, language: {}", genre, language);

        List<Movie> movies = movieRepository.findMoviesWithFilters(genre, language);

        logger.info("Found {} movies", movies.size());
        return movieMapper.toMovieDTOList(movies);
    }

    @Override
    public MovieDetailDTO getMovieById(Long movieId) {
        logger.info("Fetching movie with ID: {}", movieId);

        Movie movie = movieRepository.findByIdWithShowtimes(movieId)
                .orElseThrow(() -> {
                    logger.warn("Movie not found with ID: {}", movieId);
                    return new MovieNotFoundException("Movie not found with ID: " + movieId);
                });

        logger.info("Found movie: {} with {} showtimes", movie.getTitle(),
                   movie.getShowtimes() != null ? movie.getShowtimes().size() : 0);
        return movieMapper.toMovieDetailDTO(movie);
    }
}