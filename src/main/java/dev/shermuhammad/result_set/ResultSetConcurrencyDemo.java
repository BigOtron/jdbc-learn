package dev.shermuhammad.result_set;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class ResultSetConcurrencyDemo {
    public static void main(String[] args) {
        String query = "SELECT id, name FROM authors";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            try (Statement stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    String originalName = rs.getString("name");
                    System.out.println("Original Name in first row: " + originalName);

                    rs.updateString("name", originalName + " (Updated via JDBC)");

                    rs.updateRow();
                    System.out.println("Row updated successfully!");
                }

                System.out.println("-----------------------------------");

                System.out.println("Inserting a new author...");

                rs.moveToInsertRow();

                rs.updateInt("id", 121212);
                rs.updateString("name", "New Author");

                rs.insertRow();

                rs.moveToCurrentRow();
                System.out.println("New row inserted successfully!");

                System.out.println("-----------------------------------");
                if (rs.absolute(2)) {
                    System.out.println("Deleting the second author: " + rs.getString("name"));

                    rs.deleteRow();
                    System.out.println("Row deleted successfully!");
                }

            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getLocalizedMessage());
        }
    }
}