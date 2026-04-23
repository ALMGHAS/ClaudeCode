package com.bookmyseat.movieservice.mapper;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.dto.ShowtimeDTO;
import com.bookmyseat.movieservice.entity.Movie;
import com.bookmyseat.movieservice.entity.Showtime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public MovieDTO toMovieDTO(Movie movie) {
        if (movie == null) {
            return null;
        }
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getDurationMinutes(),
                movie.getGenre(),
                movie.getLanguage(),
                movie.getReleaseDate()
        );
    }

    public List<MovieDTO> toMovieDTOList(List<Movie> movies) {
        if (movies == null) {
            return null;
        }
        return movies.stream()
                .map(this::toMovieDTO)
                .collect(Collectors.toList());
    }

    public MovieDetailDTO toMovieDetailDTO(Movie movie) {
        if (movie == null) {
            return null;
        }

        List<ShowtimeDTO> showtimeDTOs = movie.getShowtimes() != null
                ? movie.getShowtimes().stream()
                    .map(this::toShowtimeDTO)
                    .collect(Collectors.toList())
                : null;

        return new MovieDetailDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getDurationMinutes(),
                movie.getGenre(),
                movie.getLanguage(),
                movie.getReleaseDate(),
                showtimeDTOs
        );
    }

    public ShowtimeDTO toShowtimeDTO(Showtime showtime) {
        if (showtime == null) {
            return null;
        }
        return new ShowtimeDTO(
                showtime.getId(),
                showtime.getMovie().getId(),
                showtime.getMovie().getTitle(),
                showtime.getShowDateTime(),
                showtime.getTheater(),
                showtime.getAvailableSeats()
        );
    }

    public List<ShowtimeDTO> toShowtimeDTOList(List<Showtime> showtimes) {
        if (showtimes == null) {
            return null;
        }
        return showtimes.stream()
                .map(this::toShowtimeDTO)
                .collect(Collectors.toList());
    }
}