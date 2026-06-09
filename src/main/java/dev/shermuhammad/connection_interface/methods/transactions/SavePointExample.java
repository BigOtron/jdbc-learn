package dev.shermuhammad.connection_interface.methods.transactions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Random;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;

public class SavePointExample {
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            connection.setAutoCommit(false);

            String batchInsertIntoAuthors = "INSERT INTO authors VALUES (?, ?)";
            String batchInsertIntoCities = "INSERT INTO cities VALUES (?, ?::point)";

            try(PreparedStatement batchIntoAuthors = connection.prepareStatement(batchInsertIntoAuthors);
            PreparedStatement batchIntoCities = connection.prepareStatement(batchInsertIntoCities)) {

                long authorId = 1000L;
                for (int i = 0; i < 1000; i++) {
                    batchIntoAuthors.setLong(1, authorId++);
                    batchIntoAuthors.setString(2, getRandomString());
                    batchIntoAuthors.executeUpdate();
                }

                Savepoint spAtAuthor = connection.setSavepoint();

                try {
                    for (int i = 0; i < 1000; i++) {
                        batchIntoCities.setString(1, getRandomString());
                        batchIntoCities.setString(2, getRandomPoint());
                        batchIntoCities.executeUpdate();

                        // we will simulate an exception
                        if (i == 400) {
                            throw new SQLException("Let's say we encountered a network error");
                        }
                    }
                } catch (SQLException e) {
                    // we roll back to the point where we saved all the authors, but the city update will be lost
                    connection.rollback(spAtAuthor);
                    throw e;
                }
            } catch (SQLException e) {
                System.err.println(e.getLocalizedMessage());
                connection.commit();
            }
        } catch (SQLException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private static String getRandomString() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return builder.toString();
    }

    private static String getRandomPoint() {
        Random random = new Random();

        float x = random.nextFloat() * 10;
        float y = random.nextFloat() * 10;

        return String.format("(%.4f, %.24f)", x, y);
    }
}
