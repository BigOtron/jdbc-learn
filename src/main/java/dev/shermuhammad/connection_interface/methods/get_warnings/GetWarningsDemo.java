package dev.shermuhammad.connection_interface.methods.get_warnings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class GetWarningsDemo {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            connection.clearWarnings();
            String warningQuery = """
                DO $$
                BEGIN
                    RAISE NOTICE 'Attention! This is a simulated custom database warning from Postgres.';
                END $$;
                """;
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(warningQuery);

                SQLWarning warnings = stmt.getWarnings();
                if (warnings!=null) {
                    System.out.println("Message: " + warnings.getMessage());
                    System.out.println("SQLState: " + warnings.getSQLState());
                    System.out.println("Vendor Code: " + warnings.getErrorCode());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
