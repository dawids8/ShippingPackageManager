package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class Database {

    private static final String DB_URL = "jdbc:sqlite:C:/SQLite/Databases/mydatabase.db";
    private static Connection connection;

    static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(DB_URL);
            }

            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
