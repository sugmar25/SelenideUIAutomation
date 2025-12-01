package org.gallup.access.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionPool {

    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
        config.setUsername("dbuser");
        config.setPassword("secret");
        config.setMaximumPoolSize(10);       // Max connections in pool
        config.setMinimumIdle(2);            // Min idle connections
        config.setIdleTimeout(30000);        // 30s idle timeout
        config.setConnectionTimeout(10000);  // 10s wait for connection
        config.setLeakDetectionThreshold(2000); // Detect leaked connections

        dataSource = new HikariDataSource(config);
    }

    private DBConnectionPool() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}