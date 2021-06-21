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
public class RollbackTransaction {

    private final Connection connection;

    @SneakyThrows
    public void execute() {
        final String movieTitle = "The Shawshank Redemption";
        final int movieYear = 1994;

        final int inexistentGenreId = 10000;

        final String sqlInsertMovie = "INSERT INTO movies.movie(`title`, `year`, `creation_user`) VALUES(?, ?, ?);";

        final String sqlInsertRelation = "INSERT INTO movies.movie_genre(`movie_id`, `genre_id`) " +
                " VALUES ( " +
                "     (SELECT m.id FROM movies.movie AS m WHERE m.title = ? AND m.year = ?), " +
                "     ? " +
                " )";

        PreparedStatement insertMovie = null;
        PreparedStatement insertRelation = null;

        log.info("Preparing inserts for `Movie` and unknown genre");

        try {
            log.info("Executing insert new movie: {} - Title = {}, year = {}, creation_user = {}", sqlInsertMovie, movieTitle, movieYear, APP_CREATION_USER);
            insertMovie = connection.prepareStatement(sqlInsertMovie);
            insertMovie.setString(1, movieTitle);
            insertMovie.setInt(2, movieYear);
            insertMovie.setString(3, APP_CREATION_USER);
            final int moviesInserted = insertMovie.executeUpdate();
            log.info("{} movies inserted", moviesInserted);

            log.info("Executing relation for movie `{}` with genre_id {}", movieTitle, inexistentGenreId);
            insertRelation = connection.prepareStatement(sqlInsertRelation);
            log.info("Prepared statement for relation insert created successfully");
            insertRelation.setString(1, movieTitle);
            insertRelation.setInt(2, movieYear);
            insertRelation.setInt(3, inexistentGenreId);
            final int relationsInserted = insertRelation.executeUpdate();
            log.info("{} relations inserted", relationsInserted);

            connection.commit();
        } catch (SQLException e) {
            log.error("Executing rollback - Error with statements: {}", e.getLocalizedMessage());
            connection.rollback();
        } finally {
            if (nonNull(insertMovie))
                insertMovie.close();

            if (nonNull(insertRelation))
                insertRelation.close();
        }
    }

}
