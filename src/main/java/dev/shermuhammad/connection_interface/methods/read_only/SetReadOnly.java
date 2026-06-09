package dev.shermuhammad.connection_interface.methods.read_only;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class SetReadOnly {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            connection.setReadOnly(true);
            connection.setAutoCommit(false);

            String readQuery = "SELECT COUNT(id) FROM authors";
            try (PreparedStatement countStmt = connection.prepareStatement(readQuery);
                 ResultSet rs = countStmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Total authors: " + rs.getInt(1));
                }
            }

            String writeQuery = "UPDATE authors SET id = 9999 WHERE id = 455";
            try (PreparedStatement updateIdStmt = connection.prepareStatement(writeQuery)) {
                updateIdStmt.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Successfully prevented write operation in the read-only connection");
            }

            connection.commit();
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
