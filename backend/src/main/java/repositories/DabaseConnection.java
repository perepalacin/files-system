package repositories;

import java.sql.*;

public class DabaseConnection {

    private Connection connection;

    public DabaseConnection() {
        String url = "jdbc:postgresql://localhost:5432/your_database_name";
        String user = "your_username";
        String password = "your_password";
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255));";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Table 'users' created or already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(String name) {
        String sql = "INSERT INTO users (name) VALUES ('" + name + "');";
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            System.out.println("Inserted user: " + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listUsers() {
        String sql = "SELECT * FROM users;";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            System.out.println("User List:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("User ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
