package io.redbee.academy.persistence.jdbc.examples;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static io.redbee.academy.persistence.jdbc.Demo.APP_CREATION_USER;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor(staticName = "with")
public class RollbackTransaction {

    private final Connection connection;

    @SneakyThrows
    public void execute() {
        connection.setAutoCommit(Boolean.FALSE);

        final String movieTitle = "The Shawshank Redemption";
        final int movieYear = 1994;

        final int nonExistentGenreId = 10000;

        final String sqlInsertMovie = "INSERT INTO movie(title, year, creation_user) VALUES(?, ?, ?);";
        final String sqlInsertRelation = "INSERT INTO movie_genre(movie_id, genre_id) VALUES (?, ?)";

        PreparedStatement insertMovie = null;
        PreparedStatement insertRelation = null;

        log.info("Preparing inserts for `Movie` and unknown genre");

        try {
            log.info("Executing insert new movie: {} - Title = {}, year = {}, creation_user = {}", sqlInsertMovie, movieTitle, movieYear, APP_CREATION_USER);
            insertMovie = connection.prepareStatement(sqlInsertMovie, Statement.RETURN_GENERATED_KEYS);
            insertMovie.setString(1, movieTitle);
            insertMovie.setInt(2, movieYear);
            insertMovie.setString(3, APP_CREATION_USER);
            final int moviesInserted = insertMovie.executeUpdate();
            final int generatedMovieId = extractGeneratedId(insertMovie);
            log.info("{} movies inserted - Generated id = {}", moviesInserted, generatedMovieId);

            log.info("Executing relation for movie `{}` with genre_id {}", movieTitle, nonExistentGenreId);
            insertRelation = connection.prepareStatement(sqlInsertRelation);
            log.info("Prepared statement for relation insert created successfully");
            insertRelation.setInt(1, generatedMovieId);
            insertRelation.setInt(2, nonExistentGenreId);
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

            connection.setAutoCommit(Boolean.TRUE);
        }
    }

    private Integer extractGeneratedId(Statement statement) throws SQLException {
        final ResultSet generatedKeys = statement.getGeneratedKeys();

        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        throw new SQLException("No ID obtained");
    }

}
