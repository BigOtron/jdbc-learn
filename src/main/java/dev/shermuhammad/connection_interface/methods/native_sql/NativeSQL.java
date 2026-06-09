package dev.shermuhammad.connection_interface.methods.native_sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class NativeSQL {
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String nativeSQL = connection.nativeSQL("INSERT INTO books VALUES (?, ?)");
            System.out.println(nativeSQL);

            String jdbcSQL = "SELECT {fn CURDATE()} FROM books";
            System.out.println(connection.nativeSQL(jdbcSQL));
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
