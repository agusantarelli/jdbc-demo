package io.redbee.academy.persistence.jdbc;

import io.redbee.academy.persistence.jdbc.examples.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class Demo {

    public static final String APP_CREATION_USER = "jdbc-demo-app";

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/movies";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    public static void main(String[] args) {
        getConnection().ifPresent(connection -> {
            RawSelect.with(connection).execute();
            PreparedStatementSelect.with(connection).execute();
            PreparedStatementInsert.with(connection).execute();
            SuccessfulTransaction.with(connection).execute();
            RollbackTransaction.with(connection).execute();
            close(connection);
        });
    }

    private static Optional<Connection> getConnection() {
        try {
            log.info("Creating DB connection");

            final Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            log.info("DB connection created successfully");

            return Optional.of(connection);
        } catch (SQLException e) {
            log.error("Error creating connection: {}", e.getLocalizedMessage());
            return Optional.empty();
        }
    }

    private static void close(Connection connection) {
        try {
            log.info("Closing DB connection");
            connection.close();
            log.info("DB connection closed successfully");
        } catch (SQLException e) {
            log.error("Error closing DB connection: {}", e.getLocalizedMessage());
        }
    }

}
