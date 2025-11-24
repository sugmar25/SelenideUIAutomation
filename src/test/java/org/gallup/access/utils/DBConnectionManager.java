package org.gallup.access.utils;

import java.sql.*;

public class DBConnectionManager {
    // Step 1: Private static instance
    private static DBConnectionManager instance;

    // Step 2: Private constructor to prevent external instantiation
    private DBConnectionManager() {
    }

    // Step 3: Public static method to return the single instance
    public static DBConnectionManager getInstance() {
        if (instance == null) {
            synchronized (DBConnectionManager.class) {
                if (instance == null) {
                    instance = new DBConnectionManager();
                }
            }
        }
        return instance;
    }

    public void connectToDatabase() {
        System.out.println("Connecting to database...");
        String url = "jdbc:mysql://localhost:3306/your_db";
        String user = "your_user";
        String password = "your_password";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");
            // Example query (optional)
            String query = "SELECT COUNT(*) FROM orders";
            try (PreparedStatement stmt = connection.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Total orders: " + count);
                }
            }

        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            throw new RuntimeException("DB connection error", e);
        }
    }


}
