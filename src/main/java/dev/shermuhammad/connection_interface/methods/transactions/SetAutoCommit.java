package dev.shermuhammad.connection_interface.methods.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class SetAutoCommit {
    public static void main(String[] args) {
        String insertQuery = "INSERT INTO authors (id, name) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, 101);
                statement.setString(2, "Lynne Twist");

                int rowsInserted = statement.executeUpdate();
                System.out.println("Rows staged for insertion: " + rowsInserted);
                System.out.println("Transaction is active, but NOT yet visible to other DB connections.");

                // we commit the transaction to finalize changes
                connection.commit();
                System.out.println("Transaction successfully committed to the database!");

            } catch (SQLException e) {
                // we roll back if anything inside the inner block fails
                System.err.println("An error occurred during SQL execution. Rolling back transaction...");
                connection.rollback();
                throw e; // we rethrow to let the outer catch block print the message
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getLocalizedMessage());
        }
    }
}