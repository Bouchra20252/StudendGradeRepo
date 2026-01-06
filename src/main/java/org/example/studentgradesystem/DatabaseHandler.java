package org.example.studentgradesystem;
import java.sql.*;

public class DatabaseHandler {
    private static final String URL = "jdbc:sqlite:school.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS grades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "student_name TEXT NOT NULL," +
                "subject TEXT NOT NULL," +
                "mark REAL NOT NULL);";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}