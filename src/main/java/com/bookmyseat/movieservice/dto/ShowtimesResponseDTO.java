package com.bookmyseat.movieservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response containing list of showtimes")
public class ShowtimesResponseDTO {

    @Schema(description = "List of showtimes")
    private List<ShowtimeDTO> showtimes;

    // Constructors
    public ShowtimesResponseDTO() {}

    public ShowtimesResponseDTO(List<ShowtimeDTO> showtimes) {
        this.showtimes = showtimes;
    }

    // Getters and Setters
    public List<ShowtimeDTO> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<ShowtimeDTO> showtimes) {
        this.showtimes = showtimes;
    }
}