package lab2;

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
    try (Connection conn = getConnection();
        Statement stmt = conn.createStatement()) {

      // ВИДАЛЯЄМО СТАРУ ТАБЛИЦЮ (це критично для оновлення структури)
      stmt.execute("DROP TABLE IF EXISTS items");

      // СТВОРЮЄМО ТАБЛИЦЮ З ПОЛЯМИ АУДИТУ
      stmt.execute("CREATE TABLE items (" +
          "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
          "name VARCHAR(255), " +
          "code VARCHAR(50), " +
          "description TEXT, " +
          "created_date TIMESTAMP, " +
          "created_by VARCHAR(100), " +
          "last_modified_date TIMESTAMP, " +
          "last_modified_by VARCHAR(100))");

      // ЗАПОВНЕННЯ (30 елементів)
      String insertSQL = "INSERT INTO items (name, code, description, created_date, created_by) VALUES (?, ?, ?, ?, ?)";

      try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
        for (int i = 1; i <= 30; i++) {
          pstmt.setString(1, "Тестовий об'єкт №" + i);
          pstmt.setString(2, "ITEM-CODE-" + String.format("%03d", i));
          pstmt.setString(3, "Опис елемента " + i);
          // Встановлюємо поточний час
          pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
          // Встановлюємо автора (адміна)
          pstmt.setString(5, "admin_user");
          pstmt.addBatch();
        }
        pstmt.executeBatch();
      }

      // Рядок System.out.println(...) успішно видалено звідси

    } catch (SQLException e) {
      throw new RuntimeException("Помилка ініціалізації БД: " + e.getMessage(), e);
    }
  }
}