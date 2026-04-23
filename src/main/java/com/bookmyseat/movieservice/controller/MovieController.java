package com.bookmyseat.movieservice.controller;

import com.bookmyseat.movieservice.dto.MovieDetailDTO;
import com.bookmyseat.movieservice.dto.MoviesResponseDTO;
import com.bookmyseat.movieservice.dto.ShowtimesResponseDTO;
import com.bookmyseat.movieservice.service.MovieService;
import com.bookmyseat.movieservice.service.ShowtimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Movie API", description = "APIs for managing movies and showtimes")
public class MovieController {

    private final MovieService movieService;
    private final ShowtimeService showtimeService;

    @Autowired
    public MovieController(MovieService movieService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/movies")
    @Operation(
        summary = "Get all movies",
        description = "Retrieve all movies with optional filtering by genre and language"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Movies retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MoviesResponseDTO.class))
        )
    })
    public ResponseEntity<MoviesResponseDTO> getAllMovies(
            @Parameter(description = "Filter by genre", example = "Sci-Fi")
            @RequestParam(required = false) String genre,

            @Parameter(description = "Filter by language", example = "English")
            @RequestParam(required = false) String language) {

        MoviesResponseDTO response = new MoviesResponseDTO(
            movieService.getAllMovies(genre, language)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/movies/{movieId}")
    @Operation(
        summary = "Get movie by ID",
        description = "Retrieve a specific movie with its showtimes"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Movie retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieDetailDTO.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Movie not found",
            content = @Content(mediaType = "application/json")
        )
    })
    public ResponseEntity<MovieDetailDTO> getMovieById(
            @Parameter(description = "Movie ID", example = "1")
            @PathVariable Long movieId) {

        MovieDetailDTO movie = movieService.getMovieById(movieId);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/showtimes")
    @Operation(
        summary = "Get all showtimes",
        description = "Retrieve all showtimes with optional filtering"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Showtimes retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShowtimesResponseDTO.class))
        )
    })
    public ResponseEntity<ShowtimesResponseDTO> getAllShowtimes(
            @Parameter(description = "Filter by movie ID", example = "1")
            @RequestParam(required = false) Long movieId,

            @Parameter(description = "Filter by date (YYYY-MM-DD)", example = "2025-09-30")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @Parameter(description = "Filter by theater name", example = "Theater 1")
            @RequestParam(required = false) String theater) {

        ShowtimesResponseDTO response = new ShowtimesResponseDTO(
            showtimeService.getAllShowtimes(movieId, date, theater)
        );
        return ResponseEntity.ok(response);
    }
}
