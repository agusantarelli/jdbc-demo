create table genre
(
    id            smallserial                        not null
        constraint genre_pk primary key,
    description   character varying                  not null,
    creation_user character varying default 'script' not null
);

create index genre_description_index on genre (description);

create table movie
(
    id            serial                             not null
        constraint movie_pk primary key,
    title         character varying                  not null,
    year          int,
    creation_user character varying default 'script' not null
);

create index movie_title_year_index on movie (title, year);

create table movie_genre
(
    movie_id int      not null
        constraint movie_genre_movie_id_fk references movie,
    genre_id smallint not null
        constraint movie_genre_genre_id_fk references genre,
    constraint movie_genre_pk unique (movie_id, genre_id)
);

insert into genre (description)
values
  ('Biography'),
  ('Crime'),
  ('Drama');

insert into movie (title, year)
values
  ('Fight Club', 1999),
  ('Joker', 2019),
  ('Pulp Fiction', 1994);

insert into movie_genre
    (movie_id, genre_id)
values (
    (select m.id from movie as m where m.title = 'Fight Club' and m.year = 1999),
    (select g.id from genre as g where g.description = 'Drama')
),
(
    (select m.id from movie as m where m.title = 'Joker' and m.year = 2019),
    (select g.id from genre as g where g.description = 'Crime')
),
(
    (select m.id from movie as m where m.title = 'Joker' and m.year = 2019),
    (select g.id from genre as g where g.description = 'Drama')
),
(
    (select m.id from movie as m where m.title = 'Pulp Fiction' and m.year = 1994),
    (select g.id from genre as g where g.description = 'Crime')
),
(
    (select m.id from movie as m where m.title = 'Pulp Fiction' and m.year = 1994),
    (select g.id from genre as g where g.description = 'Drama')
);