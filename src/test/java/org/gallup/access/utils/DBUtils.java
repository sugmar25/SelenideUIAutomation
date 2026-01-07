package org.gallup.access.utils;
import java.sql.*;
import java.util.*;

public class DBUtils {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    // Load DB config from properties
    private static String url = ConfigReader.get("db.url");
    private static String username = ConfigReader.get("db.username");
    private static String password = ConfigReader.get("db.password");

    // Create connection
    public static void connect() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB: " + e.getMessage());
        }
    }

    // Execute SELECT query
    public static ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + e.getMessage());
        }
    }

    // Execute INSERT/UPDATE/DELETE
    public static int executeUpdate(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + e.getMessage());
        }
    }

    // Return single value
    public static String getSingleValue(String query) {
        try {
            resultSet = executeQuery(query);
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch single value: " + e.getMessage());
        }
    }

    // Return one row as Map
    public static Map<String, String> getRowAsMap(String query) {
        Map<String, String> row = new HashMap<>();
        try {
            resultSet = executeQuery(query);
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();

            if (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), resultSet.getString(i));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch row: " + e.getMessage());
        }
        return row;
    }

    // Return table as List of Maps
    public static List<Map<String, String>> getTableAsList(String query) {
        List<Map<String, String>> table = new ArrayList<>();
        try {
            resultSet = executeQuery(query);
            ResultSetMetaData meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();

            while (resultSet.next()) {
                Map<String, String> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(meta.getColumnName(i), resultSet.getString(i));
                }
                table.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch table: " + e.getMessage());
        }
        return table;
    }

    // Close connection
    public static void close() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException ignored) {}
    }
    /*DBUtils.connect();
    String name = DBUtils.getSingleValue("SELECT name FROM users WHERE id=101");
    assertEquals("Brinda",name);
    DBUtils.close();
    Map<String, String> row = DBUtils.getRowAsMap("SELECT * FROM jobs WHERE id=5");
    assertEquals("QA Engineer",row.get("title"));
    List<Map<String, String>> jobs = DBUtils.getTableAsList("SELECT * FROM jobs");
    assertTrue(jobs.size() >0);*/
}
