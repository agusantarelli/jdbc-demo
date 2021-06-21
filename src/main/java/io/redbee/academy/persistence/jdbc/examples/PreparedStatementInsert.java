package io.redbee.academy.persistence.jdbc.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static io.redbee.academy.persistence.jdbc.Demo.APP_CREATION_USER;

@Slf4j
@RequiredArgsConstructor(staticName = "with")
public class PreparedStatementInsert {

    private final Connection connection;

    public void execute() {
        final String query = "INSERT INTO movie (`title`, `year`, `creation_user`) VALUES (?, ?, ?);";

        final String title = "Se7en";
        final int year = 1995;

        log.info("Executing prepared statement: {} - With title = {}, year = {}, creation_user = {}", query, title, year, APP_CREATION_USER);

        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setInt(2, year);
            statement.setString(3, APP_CREATION_USER);

            final int affectedRows = statement.executeUpdate();

            statement.close();

            log.info("Number of affected rows: {}", affectedRows);
        } catch (SQLException e) {
            log.error("Error executing prepared statement: {}", e.getLocalizedMessage());
        }
    }

}
