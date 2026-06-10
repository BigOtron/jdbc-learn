package dev.shermuhammad.connection_interface.methods.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class DirtyReadSimulation {
    public static void main(String[] args) {
        Thread transaction1 = new Thread(() -> {
           try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
               connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
               connection.setAutoCommit(false);
               String updateQuery = "UPDATE authors SET name = ? WHERE id = ?";

               try(PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                   stmt.setString(1, "William Shakespeare");
                   stmt.setLong(2, 1002);

                   int successRows = stmt.executeUpdate();
                   System.out.println("Successfully updated rows: " + successRows);
               }
               // we will let another connection read the not commited data
               Thread.sleep(1000 * 5);

               if (true) {
                   connection.rollback();
                   throw new SQLException("Alert, alert, network issue happened. Roll back the updates");
               }

           } catch (SQLException | InterruptedException e) {
               System.out.println(e.getLocalizedMessage());
           }
        });

        Thread transaction2 = new Thread(() -> {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                connection.setReadOnly(true);
                Thread.sleep(1000);
                String readQuery = "SELECT name FROM authors WHERE id = 1002";
                try(PreparedStatement stmt = connection.prepareStatement(readQuery);
                    ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        System.out.println("The name of the author with ID 1002: " + rs.getString("name"));
                    }
                }

                Thread.sleep(1000 * 5);
                try(PreparedStatement stmt = connection.prepareStatement(readQuery);
                    ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        System.out.println("The name of the author with ID 1002: " + rs.getString("name"));
                    }
                }
            } catch (SQLException | InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        });

        transaction1.start();
        transaction2.start();
    }
}
