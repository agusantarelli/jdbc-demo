CREATE SCHEMA IF NOT EXISTS movies;

USE movies;

CREATE TABLE genre (
  id SMALLINT AUTO_INCREMENT,
  description VARCHAR(25) NOT NULL,
  creation_user VARCHAR(20) NOT NULL DEFAULT 'script',
  CONSTRAINT genre_pk PRIMARY KEY (id)
);

CREATE UNIQUE INDEX genre_description_uindex ON genre (description);

CREATE TABLE movie (
  id INT AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  year YEAR NULL,
  creation_user VARCHAR(20) NOT NULL DEFAULT 'script',
  CONSTRAINT movie_pk PRIMARY KEY (id)
);

CREATE TABLE movie_genre (
    movie_id INT NOT NULL,
    genre_id SMALLINT NOT NULL,
    CONSTRAINT movie_genre_pk PRIMARY KEY (movie_id, genre_id),
    CONSTRAINT movie_genre_genre_id_fk FOREIGN KEY (genre_id) REFERENCES genre (id),
    CONSTRAINT movie_genre_movie_id_fk FOREIGN KEY (movie_id) REFERENCES movie (id)
);

INSERT INTO `movies`.`genre` (`description`)
VALUES
  ('Biography'),
  ('Crime'),
  ('Drama');

INSERT INTO `movies`.`movie` (`title`, `year`)
VALUES
  ('Fight Club', 1999),
  ('Joker', 2019),
  ('Pulp Fiction', 1994);

INSERT INTO movies.movie_genre
    (`movie_id`, `genre_id`)
VALUES (
    (SELECT m.id FROM movies.movie AS m WHERE m.title = 'Fight Club' AND m.year = 1999),
    (SELECT g.id FROM movies.genre AS g WHERE g.description = 'Drama')
),
(
    (SELECT m.id FROM movies.movie AS m WHERE m.title = 'Joker' AND m.year = 2019),
    (SELECT g.id FROM movies.genre AS g WHERE g.description = 'Crime')
),
(
    (SELECT m.id FROM movies.movie AS m WHERE m.title = 'Joker' AND m.year = 2019),
    (SELECT g.id FROM movies.genre AS g WHERE g.description = 'Drama')
),
(
    (SELECT m.id FROM movies.movie AS m WHERE m.title = 'Pulp Fiction' AND m.year = 1994),
    (SELECT g.id FROM movies.genre AS g WHERE g.description = 'Crime')
),
(
    (SELECT m.id FROM movies.movie AS m WHERE m.title = 'Pulp Fiction' AND m.year = 1994),
    (SELECT g.id FROM movies.genre AS g WHERE g.description = 'Drama')
);
