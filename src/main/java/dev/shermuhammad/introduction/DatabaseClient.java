package dev.shermuhammad.introduction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseClient {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/macwin";
        String user = "macwin";
        String password = "";

        String query = "SELECT * FROM cities";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery(query))
        {
            while (set.next()) {
                System.out.println(set.getObject(1));
            }
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the database because of " + e.getLocalizedMessage());
        }
    }
}
