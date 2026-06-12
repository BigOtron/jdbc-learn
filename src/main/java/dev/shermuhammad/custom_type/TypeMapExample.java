package dev.shermuhammad.custom_type;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static dev.shermuhammad.DatabaseProperties.PASSWORD;
import static dev.shermuhammad.DatabaseProperties.URL;
import static dev.shermuhammad.DatabaseProperties.USERNAME;
/*
THIS DEMO DID NOT WORK WITH POSTGRESQL. IT TURNS OUT JDBC DRIVER FOR POSTGRESQL DOESN'T SUPPORT setTypeMap().
The alternative solution would be to flatten the database so we don't use composite types. Another solution is to
implement the PGobject, which would tightly couple our app to the postgresql database.
 */
public class TypeMapExample {
    public static void main(String[] args) {

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Map<String, Class<?>> myMap = con.getTypeMap();
            myMap.put("PUBLIC.POINT_TYPE", Point.class);
            con.setTypeMap(myMap);

            String query = "SELECT * FROM coordinate_plane";
            try (PreparedStatement stmt = con.prepareStatement(query)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Point point = (Point) rs.getObject("point");
                        String label = rs.getString("label");
                        System.out.printf("Point coordinates for %s: X: %d and Y: %d%n", label, point.getX(), point.getY());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
