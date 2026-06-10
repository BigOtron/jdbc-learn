package dev.shermuhammad.connection_interface.methods.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class NonRepeatableReadSimulation {
    public static void main(String[] args) {
        Thread transaction1 = new Thread(() -> {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                connection.setReadOnly(true);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                connection.setAutoCommit(false);
                String readQuery = "SELECT name FROM authors where id = 1020";

                try(PreparedStatement stmt = connection.prepareStatement(readQuery);
                    ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("[T1] Author name: " + rs.getString("name"));
                    }
                }

                Thread.sleep(1000 * 3);
                System.out.println("[T1] After three seconds, we query again.");

                try(PreparedStatement stmt = connection.prepareStatement(readQuery);
                    ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("[T1] Author name: " + rs.getString("name"));
                    }
                }

                connection.commit();
            } catch (SQLException | InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        });

        Thread transaction2 = new Thread(() -> {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                Thread.sleep(1000);
                connection.setAutoCommit(false);
                String updateQuery = "UPDATE authors SET name = ? WHERE id = ?";

                try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
                    stmt.setString(1, "Lev Tolstoy");
                    stmt.setLong(2, 1020L);

                    int successRow = stmt.executeUpdate();
                    System.out.println("[T2] Successfully updated rows: " + successRow);
                }

                connection.commit();
            } catch (SQLException | InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        });

        transaction1.start();
        transaction2.start();
    }
}
