package repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepository {

    private static final String SQL_SELECT_QUERY = "SELECT * FROM userLogin WHERE userName = ?";
    private static final String ADD_NEW_USER = "INSERT INTO userLogin (userName, userPassword) VALUES (?, ?)";

    public static void addNewUser(String name, String password) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(ADD_NEW_USER);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfUserNameExist(String name) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(SQL_SELECT_QUERY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String userNameFromDb = resultSet.getString("userName");
                if (name.equals(userNameFromDb)) {
                    return false;
                }
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public static boolean checkIfUserDataIsCorrect(String name, String password) {
        try {
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(SQL_SELECT_QUERY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String passwordFromDb = resultSet.getString("userPassword");
                if (password.equals(passwordFromDb)) {
                    return true;
                }
            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}