package com.bookmyseat.movieservice.repository;

import com.bookmyseat.movieservice.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE " +
           "(:genre IS NULL OR m.genre = :genre) AND " +
           "(:language IS NULL OR m.language = :language)")
    List<Movie> findMoviesWithFilters(@Param("genre") String genre,
                                     @Param("language") String language);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.showtimes WHERE m.id = :id")
    Optional<Movie> findByIdWithShowtimes(@Param("id") Long id);

    List<Movie> findByGenre(String genre);

    List<Movie> findByLanguage(String language);

    List<Movie> findByGenreAndLanguage(String genre, String language);
}