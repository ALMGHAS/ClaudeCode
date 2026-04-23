package com.bookmyseat.movieservice.repository;

import com.bookmyseat.movieservice.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie WHERE " +
           "(:movieId IS NULL OR s.movie.id = :movieId) AND " +
           "(:date IS NULL OR DATE(s.showDateTime) = :date) AND " +
           "(:theater IS NULL OR s.theater = :theater)")
    List<Showtime> findShowtimesWithFilters(@Param("movieId") Long movieId,
                                           @Param("date") LocalDate date,
                                           @Param("theater") String theater);

    List<Showtime> findByMovieId(Long movieId);

    @Query("SELECT s FROM Showtime s WHERE DATE(s.showDateTime) = :date")
    List<Showtime> findByShowDate(@Param("date") LocalDate date);

    List<Showtime> findByTheater(String theater);

    @Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId AND DATE(s.showDateTime) = :date")
    List<Showtime> findByMovieIdAndShowDate(@Param("movieId") Long movieId, @Param("date") LocalDate date);

    @Query("SELECT s FROM Showtime s WHERE s.movie.id = :movieId AND s.theater = :theater")
    List<Showtime> findByMovieIdAndTheater(@Param("movieId") Long movieId, @Param("theater") String theater);

    @Query("SELECT s FROM Showtime s WHERE DATE(s.showDateTime) = :date AND s.theater = :theater")
    List<Showtime> findByShowDateAndTheater(@Param("date") LocalDate date, @Param("theater") String theater);

    @Query("SELECT s FROM Showtime s WHERE s.showDateTime >= :startTime AND s.showDateTime < :endTime")
    List<Showtime> findByShowDateTimeBetween(@Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);
}