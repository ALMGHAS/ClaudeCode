package com.bookmyseat.movieservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response containing list of movies")
public class MoviesResponseDTO {

    @Schema(description = "List of movies")
    private List<MovieDTO> movies;

    // Constructors
    public MoviesResponseDTO() {}

    public MoviesResponseDTO(List<MovieDTO> movies) {
        this.movies = movies;
    }

    // Getters and Setters
    public List<MovieDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieDTO> movies) {
        this.movies = movies;
    }
}