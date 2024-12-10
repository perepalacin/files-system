package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersRepository {

    public void insertUser(String email, String password) {
        String sql = "INSERT INTO users (email), (password) VALUES (?), (?);";
        try (Connection connection = DatabaseConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            System.out.println("Inserted user: " + email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
