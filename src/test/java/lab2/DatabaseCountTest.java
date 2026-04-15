package lab2;

/*
 @author Sandoplay
 @project lab1
 @class DatabaseCountTest
 @version 1.0.0
 @since 15.04.2026 - 16.29
*/

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseCountTest {

  @BeforeAll
  static void setupDatabase() {
    DatabaseManager.initDatabase();
  }

  @Test
  void whenGetAllItemsListThenSizeIs30() {
    int count = 0;

    try (Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM items")) {

      if (rs.next()) {
        count = rs.getInt(1);
      }

    } catch (SQLException e) {
      throw new RuntimeException("Помилка під час виконання запиту", e);
    }

    assertThat(count)
        .as("Перевірка, що база містить рівно 30 елементів")
        .isEqualTo(30);
  }
}