package com.nataliia.dao;

import com.nataliia.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    public void addUser(User user) {
        Connection connection = DbConnector.connect().get();
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO users(name, password) VALUES (\'" + user.getName() + "\',\'" + user.getPassword() + "\');";
            System.out.println(sql);
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
