package com.nataliia.dao;

import com.nataliia.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {
    private static final Logger logger = Logger.getLogger(UserDao.class);

    public boolean addUser(User user) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return true;
        } catch (SQLException e) {
            logger.error("Can't get user name", e);
        }
        return false;
    }

    public Optional<User> getUser(Long id) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            if (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                User user = new User(userID, name, email, password);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get user by his ID ", e);
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String name, String password) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "SELECT users.id, email, role\n" +
                    "FROM users\n" +
                    " JOIN roles\n" +
                    "    ON users.role_id = roles.id\n" +
                    "where users.name = ? and users.password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            if (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String email = resultSet.getString(2);
                String role = resultSet.getString(3);
                User user = new User(userID, name, email, password, role);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get user by name and password", e);
        }
        return Optional.empty();
    }

    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        Connection connection = DbConnector.connect().get();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM users";
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            logger.debug(sql);
            while (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                String password = resultSet.getString(4);
                User user = new User(userID, name, email, password);
                usersList.add(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get users from DB", e);
        }
        return usersList;
    }

    public boolean updateUser(User user) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "UPDATE users SET name = ?, password = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getId());
            logger.debug(sql);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't update user information", e);
        }
        return false;
    }

    public boolean deleteUser(long id) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            logger.debug(sql);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't delete user information", e);
        }
        return false;
    }
}
