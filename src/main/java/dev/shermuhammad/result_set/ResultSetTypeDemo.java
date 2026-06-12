package dev.shermuhammad.result_set;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class ResultSetTypeDemo {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM authors WHERE length(name) > 10";

            try (Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                 ResultSet rs = stmt.executeQuery(query)) {

                while (rs.next()) {
                    System.out.println(rs.getString("name"));
                }

                while (rs.previous()) {
                    System.out.println(rs.getString("name"));
                }

                if (rs.absolute(3)) {
                    System.out.println(rs.getString("name"));
                }

                if (rs.last()) {
                    System.out.println(rs.getString("name"));
                }

                if (rs.relative(-3)) {
                    System.out.println(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
