package dev.shermuhammad.connection_interface.methods.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class PhantomReadSimulation {
    public static void main(String[] args) {
        Thread transaction1 = new Thread(() -> {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                connection.setReadOnly(true);
                connection.setAutoCommit(false);

                String readQuery = "SELECT name from authors WHERE name LIKE ?";
                try(PreparedStatement stmt = connection.prepareStatement(readQuery)) {
                    stmt.setString(1, "Le%");

                    try (ResultSet rs = stmt.executeQuery()) {
                        System.out.println("[T1] Printing author names before the update.");
                        while (rs.next()) {
                            System.out.println(rs.getString("name"));
                        }
                    }
                }

                Thread.sleep(1000 * 3);

                try(PreparedStatement stmt = connection.prepareStatement(readQuery)) {
                    stmt.setString(1, "Le%");

                    try (ResultSet rs = stmt.executeQuery()) {
                        System.out.println("[T1] Printing author names after the update.");
                        while (rs.next()) {
                            System.out.println(rs.getString("name"));
                        }
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
                String insertQuery = "INSERT INTO authors VALUES (?, ?)";
                try (PreparedStatement stmt = connection.prepareStatement(insertQuery)) {
                    stmt.setLong(1, 7000);
                    stmt.setString(2, "Lev Lvovich Levov");

                    int successInsert = stmt.executeUpdate();
                    System.out.println("[T2] Successfully insertion: " + successInsert);
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
