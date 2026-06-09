package dev.shermuhammad.connection_interface.methods.create_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class CreateStatement {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM books")) {
            
            while(rs.next()) {
                System.out.printf("ISBN: %d Author ID: %d%n",
                        rs.getLong("isbn"), rs.getInt("author_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }

    }
}
