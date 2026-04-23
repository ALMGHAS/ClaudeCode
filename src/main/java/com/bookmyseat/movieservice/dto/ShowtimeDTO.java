package com.bookmyseat.movieservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Showtime information")
public class ShowtimeDTO {

    @Schema(description = "Showtime ID", example = "1")
    private Long id;

    @Schema(description = "Movie ID", example = "1")
    private Long movieId;

    @Schema(description = "Movie title", example = "Inception")
    private String movieTitle;

    @Schema(description = "Show date and time", example = "2025-09-30T14:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime showDateTime;

    @Schema(description = "Theater name", example = "Theater 1")
    private String theater;

    @Schema(description = "Available seats", example = "100")
    private Integer availableSeats;

    // Constructors
    public ShowtimeDTO() {}

    public ShowtimeDTO(Long id, Long movieId, String movieTitle, LocalDateTime showDateTime,
                      String theater, Integer availableSeats) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.showDateTime = showDateTime;
        this.theater = theater;
        this.availableSeats = availableSeats;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public LocalDateTime getShowDateTime() {
        return showDateTime;
    }

    public void setShowDateTime(LocalDateTime showDateTime) {
        this.showDateTime = showDateTime;
    }

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }
}