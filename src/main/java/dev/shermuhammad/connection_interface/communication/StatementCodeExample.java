package dev.shermuhammad.connection_interface.communication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class StatementCodeExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/macwin";
        Properties properties = new Properties();
        properties.put("user", "macwin");
        properties.put("password", "");

        String checkTableQuery = "DROP TABLE IF EXISTS temporary";
        String createTableQuery = "CREATE TABLE temporary(" +
                "username varchar(80)," +
                "password varchar(20))";
        String insertDataQuery1 = "INSERT INTO temporary VALUES ('alex', '12345')";
        String insertDataQuery2 = "INSERT INTO temporary VALUES ('tom', 'cat')";
        String insertDataQuery3 = "INSERT INTO temporary VALUES ('jerry', 'mouse')";
        //String deleteTableQuery = "DROP TABLE temporary";

        try(Connection connection = DriverManager.getConnection(url, properties);
            Statement statement = connection.createStatement()) {
            statement.execute(checkTableQuery);
            statement.execute(createTableQuery);
            statement.execute(insertDataQuery1);
            statement.execute(insertDataQuery2);
            statement.execute(insertDataQuery3);
            //statement.execute(deleteTableQuery);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to the db with this reason -> " + e.getLocalizedMessage());
        }
    }
}
