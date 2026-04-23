package com.bookmyseat.movieservice.service;

import com.bookmyseat.movieservice.dto.MovieDTO;
import com.bookmyseat.movieservice.dto.MovieDetailDTO;

import java.util.List;

public interface MovieService {

    List<MovieDTO> getAllMovies(String genre, String language);

    MovieDetailDTO getMovieById(Long movieId);
}