package edu.levytskyi.repository;

/*
 @author Sandoplay
 @project lab1
 @class DatabaseManager
 @version 1.0.0
 @since 15.04.2026 - 16.28
*/

import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseManager {
  private static final String URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final String USER = "sa";
  private static final String PASSWORD = "";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  public static void initDatabase() {
    try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
      stmt.execute("DROP TABLE IF EXISTS companies");

      stmt.execute("CREATE TABLE companies (" +
          "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "name VARCHAR(255), code VARCHAR(50), employees_count BIGINT, " +
          "created_date TIMESTAMP, created_by VARCHAR(100))");

      String insertSQL = "INSERT INTO companies (name, code, employees_count, created_date, created_by) VALUES (?, ?, ?, ?, ?)";
      try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
        for (int i = 1; i <= 30; i++) {
          pstmt.setString(1, "Компанія №" + i);
          pstmt.setString(2, "COMP-" + String.format("%03d", i));
          pstmt.setLong(3, 50L + i * 10); // Різна кількість співробітників
          pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
          pstmt.setString(5, "admin_user");
          pstmt.addBatch();
        }
        pstmt.executeBatch();
      }
    } catch (SQLException e) {
      throw new RuntimeException("Помилка ініціалізації БД", e);
    }
  }
}