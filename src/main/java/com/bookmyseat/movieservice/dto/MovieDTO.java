package com.bookmyseat.movieservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Movie information")
public class MovieDTO {

    @Schema(description = "Movie ID", example = "1")
    private Long id;

    @Schema(description = "Movie title", example = "Inception")
    private String title;

    @Schema(description = "Movie description", example = "A mind-bending thriller")
    private String description;

    @Schema(description = "Duration in minutes", example = "148")
    private Integer durationMinutes;

    @Schema(description = "Movie genre", example = "Sci-Fi")
    private String genre;

    @Schema(description = "Movie language", example = "English")
    private String language;

    @Schema(description = "Release date", example = "2010-07-16")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    // Constructors
    public MovieDTO() {}

    public MovieDTO(Long id, String title, String description, Integer durationMinutes,
                   String genre, String language, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.genre = genre;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}