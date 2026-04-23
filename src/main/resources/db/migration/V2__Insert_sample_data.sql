-- Insert sample movies
INSERT INTO movies (title, description, duration_minutes, genre, language, release_date) VALUES
('Inception', 'A mind-bending thriller about dreams within dreams', 148, 'Sci-Fi', 'English', '2010-07-16'),
('The Dark Knight', 'Batman faces the Joker in this epic superhero film', 152, 'Action', 'English', '2008-07-18'),
('Interstellar', 'A team of explorers travel through a wormhole in space', 169, 'Sci-Fi', 'English', '2014-11-07'),
('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control', 175, 'Drama', 'English', '1972-03-24'),
('Pulp Fiction', 'The lives of two mob hitmen, a boxer, and others intertwine', 154, 'Crime', 'English', '1994-10-14'),
('Dangal', 'A biographical sports drama about wrestler Mahavir Singh Phogat', 161, 'Biography', 'Hindi', '2016-12-23'),
('3 Idiots', 'Two friends are searching for their long lost companion', 170, 'Comedy', 'Hindi', '2009-12-25'),
('Avengers: Endgame', 'The Avengers assemble once more to reverse Thanos snap', 181, 'Action', 'English', '2019-04-26');

-- Insert sample showtimes
INSERT INTO showtimes (movie_id, show_date_time, theater, available_seats) VALUES
-- Inception showtimes
(1, '2025-09-30 14:00:00', 'Theater 1', 100),
(1, '2025-09-30 17:30:00', 'Theater 1', 85),
(1, '2025-09-30 21:00:00', 'Theater 2', 120),
(1, '2025-10-01 15:00:00', 'Theater 1', 100),

-- The Dark Knight showtimes
(2, '2025-09-30 13:30:00', 'Theater 3', 150),
(2, '2025-09-30 16:45:00', 'Theater 3', 140),
(2, '2025-09-30 20:15:00', 'Theater 3', 135),
(2, '2025-10-01 14:30:00', 'Theater 3', 150),

-- Interstellar showtimes
(3, '2025-09-30 18:00:00', 'Theater 2', 110),
(3, '2025-09-30 21:30:00', 'Theater 4', 95),
(3, '2025-10-01 16:00:00', 'Theater 2', 110),

-- The Godfather showtimes
(4, '2025-09-30 19:00:00', 'Theater 5', 80),
(4, '2025-10-01 19:30:00', 'Theater 5', 75),

-- Pulp Fiction showtimes
(5, '2025-09-30 15:30:00', 'Theater 4', 90),
(5, '2025-09-30 18:45:00', 'Theater 4', 85),
(5, '2025-10-01 17:00:00', 'Theater 4', 90),

-- Dangal showtimes
(6, '2025-09-30 16:00:00', 'Theater 6', 130),
(6, '2025-09-30 19:30:00', 'Theater 6', 125),
(6, '2025-10-01 18:00:00', 'Theater 6', 130),

-- 3 Idiots showtimes
(7, '2025-09-30 14:30:00', 'Theater 7', 140),
(7, '2025-09-30 18:00:00', 'Theater 7', 135),
(7, '2025-10-01 15:30:00', 'Theater 7', 140),

-- Avengers: Endgame showtimes
(8, '2025-09-30 13:00:00', 'Theater 8', 200),
(8, '2025-09-30 16:30:00', 'Theater 8', 190),
(8, '2025-09-30 20:00:00', 'Theater 8', 180),
(8, '2025-10-01 13:30:00', 'Theater 8', 200);