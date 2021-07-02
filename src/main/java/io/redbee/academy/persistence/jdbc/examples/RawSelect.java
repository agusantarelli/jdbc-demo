package io.redbee.academy.persistence.jdbc.examples;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@RequiredArgsConstructor(staticName = "with")
public class RawSelect {

    private final Connection connection;

    public void execute() {
        final String query = "SELECT id, description, creation_user FROM genre;";

        log.info("Executing raw query: {}", query);

        try {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(query);

            log.info("Results: ");

            while (resultSet.next()) {
                final Integer id = resultSet.getInt("id");
                final String description = resultSet.getString("description");
                final String creation_user = resultSet.getString("creation_user");

                log.info("\t id: {}, description: {}, creation_user: {}", id, description, creation_user);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            log.error("Error executing raw query: {}", e.getLocalizedMessage());
        }
    }

}
