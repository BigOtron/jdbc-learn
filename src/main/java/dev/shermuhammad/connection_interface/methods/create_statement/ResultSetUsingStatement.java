package dev.shermuhammad.connection_interface.methods.create_statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class ResultSetUsingStatement {
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM books")) {

            rs.next();
            try {
                rs.previous();
            } catch (SQLException e) {
                System.out.println("The result set is of type FORWARD_ONLY");
                System.out.println(e.getLocalizedMessage());
            }

            try {
                rs.updateLong("isbn", 0L);
            } catch (SQLException e) {
                System.out.println("The result set is read-only");
                System.out.println(e.getLocalizedMessage());
            }


        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
