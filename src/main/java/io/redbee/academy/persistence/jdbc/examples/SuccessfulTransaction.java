package io.redbee.academy.persistence.jdbc.examples;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.redbee.academy.persistence.jdbc.Demo.APP_CREATION_USER;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor(staticName = "with")
public class SuccessfulTransaction {

    private final Connection connection;

    @SneakyThrows
    public void execute() {
        final String movieTitle = "Schindler's List";
        final int movieYear = 1993;

        final String genreDescription = "History";

        final String sqlInsertMovie = "INSERT INTO movies.movie(`title`, `year`, `creation_user`) VALUES(?, ?, ?);";

        final String sqlInsertGenre = "INSERT INTO movies.genre(`description`, `creation_user`) VALUES (?, ?);";

        final String sqlInsertRelation = "INSERT INTO movies.movie_genre(`movie_id`, `genre_id`) " +
                " VALUES ( " +
                "     (SELECT m.id FROM movies.movie AS m WHERE m.title = ? AND m.year = ?), " +
                "     (SELECT g.id FROM movies.genre AS g WHERE g.description = ?) " +
                " )";

        PreparedStatement insertMovie = null;
        PreparedStatement insertGenre = null;
        PreparedStatement insertRelation = null;

        log.info("Preparing inserts for `Movie` and `Genre`");

        try {
            log.info("Executing movie insertion: {} - With title = {}, year = {}, creation_user = {}", sqlInsertMovie, movieTitle, movieYear, APP_CREATION_USER);
            insertMovie = connection.prepareStatement(sqlInsertMovie);
            insertMovie.setString(1, movieTitle);
            insertMovie.setInt(2, movieYear);
            insertMovie.setString(3, APP_CREATION_USER);
            final int moviesInserted = insertMovie.executeUpdate();
            log.info("{} movies inserted", moviesInserted);

            log.info("Executing genre insertion: {} - With description = {}, creation_user = {}", sqlInsertGenre, genreDescription, APP_CREATION_USER);
            insertGenre = connection.prepareStatement(sqlInsertGenre);
            insertGenre.setString(1, genreDescription);
            insertGenre.setString(2, APP_CREATION_USER);
            final int genresInserted = insertGenre.executeUpdate();
            log.info("{} genres inserted", genresInserted);

            log.info("Executing relation for movie `{}` and genre `{}`", movieTitle, genreDescription);
            insertRelation = connection.prepareStatement(sqlInsertRelation);
            insertRelation.setString(1, movieTitle);
            insertRelation.setInt(2, movieYear);
            insertRelation.setString(3, genreDescription);
            final int relationsInserted = insertRelation.executeUpdate();
            log.info("{} relations inserted", relationsInserted);

            connection.commit();
        } catch (SQLException e) {
            log.error("Executing rollback - Error with statements: {}", e.getLocalizedMessage());
            connection.rollback();
        } finally {
            if (nonNull(insertMovie))
                insertMovie.close();

            if (nonNull(insertGenre))
                insertGenre.close();

            if (nonNull(insertRelation))
                insertRelation.close();
        }
    }

}
