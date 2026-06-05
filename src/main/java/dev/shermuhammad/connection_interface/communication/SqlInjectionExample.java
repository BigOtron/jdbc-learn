package dev.shermuhammad.connection_interface.communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlInjectionExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/macwin";
        String username = "macwin";
        String password = "";

        try(Connection connection = DriverManager.getConnection(url, username, password)) {
            boolean legal = login(connection, "tom", "cat"); // legal attempt to log in
            boolean failedAttempt = login(connection, "mickey", "mouse");
            boolean injectionAttempt = login(connection, "tom", "password' OR '1'='1");

            System.out.println("Legal attempt: " + legal);
            System.out.println("Failed attempt: " + failedAttempt);
            System.out.println("Injection attempt: " + injectionAttempt);
        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to the db: " + e.getLocalizedMessage());
        }
    }

    public static boolean login(Connection connection, String username, String password) {
        try(Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM temporary WHERE username= '" + username + "' AND password = '" + password + "'";
            System.out.println("Executed sql query: " + sql);
            try(ResultSet set = statement.executeQuery(sql)) {
                return set.next(); // if any row is found based on the existing login, then the login is successful
            }
        } catch (SQLException e) {
            System.out.println("Error occurred while connecting to the db: " + e.getLocalizedMessage());
        }
        return false;
    }
}
