package dev.shermuhammad.connection_interface.communication;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;

public class CallableStatementExample {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/macwin";
        String username = "macwin";
        String password = "";

        String sqlCall = "{? = call total_salary()}";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            CallableStatement stmt = connection.prepareCall(sqlCall)) {
                stmt.registerOutParameter(1, Types.NUMERIC);
                stmt.execute();
                BigDecimal totalSalary = stmt.getBigDecimal(1);
                System.out.println("Total salary across the company: $" + totalSalary);
        } catch (SQLException e) {
            System.out.println("Error: -> " + e.getLocalizedMessage());
        }

    }
}
