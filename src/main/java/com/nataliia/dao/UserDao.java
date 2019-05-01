package com.nataliia.dao;

import com.nataliia.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    public boolean addUser(User user) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "INSERT INTO users(name, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
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
            if (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User(userID, name, password);
                System.out.println(user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String name, String password) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "SELECT users.id, role\n" +
                    "FROM users\n" +
                    " JOIN roles\n" +
                    "    ON users.role_id = roles.id\n" +
                    "where users.name = ? and users.password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String role = resultSet.getString(2);
                User user = new User(userID, name, password, role);
                System.out.println(user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            while (resultSet.next()) {
                Long userID = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User(userID, name, password);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(long id) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
