package com.nataliia.dao;

import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
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

    public Connection getConnection() {
        return DbConnector.connect().get();
    }

    public boolean addUser(User user) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO users(name, email, password, salt) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, HashUtil.getSHA512SecurePassword(user.getPassword(), user.getSalt()));
            preparedStatement.setString(4, user.getSalt());
            preparedStatement.executeUpdate();
            logger.debug(sql);
            return true;
        } catch (SQLException e) {
            logger.error("Can't add user", e);
        }
        return false;
    }

    public Optional<User> getUser(Long id) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            if (resultSet.next()) {
                Long userID = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                User user = new User(userID, name, email, password);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get user by his ID ", e);
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String name) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT users.id, email, password, role, salt\n" +
                    "FROM users\n" +
                    " JOIN roles\n" +
                    "    ON users.role_id = roles.id\n" +
                    "where users.name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            if (resultSet.next()) {
                Long userID = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String salt = resultSet.getString("salt");
                User user = new User(userID, name, email, password, role, salt);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get user by name", e);
        }
        return Optional.empty();
    }

    public List<User> getUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = getConnection()) {
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
        try (Connection connection = getConnection()) {
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
        try (Connection connection = getConnection()) {
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
