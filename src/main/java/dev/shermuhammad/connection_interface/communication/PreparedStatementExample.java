package dev.shermuhammad.connection_interface.communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/macwin";
        String username = "macwin";
        String password = "";

        String query = "SELECT * FROM temporary WHERE username = ? AND password = ?";

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "tom");
            preparedStatement.setString(2, "cat");

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                System.out.println(dbUsername + " " + dbPassword);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}
