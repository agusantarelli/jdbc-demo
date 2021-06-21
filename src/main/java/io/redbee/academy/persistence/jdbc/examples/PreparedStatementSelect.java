package io.redbee.academy.persistence.jdbc.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor(staticName = "with")
public class PreparedStatementSelect {

    private final Connection connection;

    public void execute() {
        final String query = "SELECT `id`, `title`, `year`, `creation_user` FROM movie " +
                "WHERE `year` BETWEEN ? AND ? " +
                "ORDER BY `year`;";

        final int firstParam = 1990;
        final int secondParam = 2000;

        log.info("Executing prepared statement: {} - First param: {} - Second param {}", query, firstParam, secondParam);

        try {
            final PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, firstParam);
            statement.setInt(2, secondParam);

            final ResultSet resultSet = statement.executeQuery();

            log.info("Results: ");

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final String title = resultSet.getString("title");
                final Integer year = resultSet.getInt("year");
                final String creation_user = resultSet.getString("creation_user");

                log.info("\t id: {}, title: {}, year: {}, creation_user: {}", id, title, year, creation_user);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error("Error executing prepared statement: {}", e.getLocalizedMessage());
        }
    }

}
