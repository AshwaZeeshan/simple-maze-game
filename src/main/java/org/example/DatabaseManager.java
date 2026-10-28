package org.example;

import java.sql.*;

/**
 * Manages database operations for the Maze Game application.
 * Handles user authentication, registration, and level progression tracking
 * using SQLite database.
 *
 * <p>Database schema includes:
 * <ul>
 * <li>users table with username, password, and unlockedLevel fields</li>
 * </ul>
 *
 * @author Mahnoor Khan, Shifa Zeeshan
 * @version 1.0
 * @since 1.0
 */
public class DatabaseManager {
    /** SQLite database connection */
    private Connection conn;

    /**
     * Establishes connection to the SQLite database and creates the users table
     * if it doesn't exist. The database file is named "users.db".
     *
     * <p>Table structure:
     * <ul>
     * <li>username (TEXT PRIMARY KEY) - unique username</li>
     * <li>password (TEXT) - user password</li>
     * <li>unlockedLevel (INTEGER DEFAULT 1) - highest unlocked level</li>
     * </ul>
     */
    public void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, unlockedLevel INTEGER DEFAULT 1)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param username the username to authenticate
     * @param password the password to verify
     * @return true if authentication successful, false otherwise
     */
    public boolean loginUser(String username, String password) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registers a new user with the provided credentials.
     * The new user starts with level 1 unlocked by default.
     *
     * @param username the username for the new account
     * @param password the password for the new account
     * @return true if registration successful, false if credentials are blank or the username already exists
     */
    public boolean signupUser(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Retrieves the highest unlocked level for a specific user.
     *
     * @param username the username to query
     * @return the highest unlocked level number, or 1 if user not found
     */
    public int getUnlockedLevel(String username) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT unlockedLevel FROM users WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * Updates the highest unlocked level for a specific user.
     * This is typically called when a user completes a level.
     *
     * @param username the username to update
     * @param level the new highest unlocked level
     */
    public void updateUnlockedLevel(String username, int level) {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE users SET unlockedLevel = ? WHERE username = ?")) {
            ps.setInt(1, level);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the database connection safely.
     * Should be called when the application is shutting down.
     */
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

 }
}
