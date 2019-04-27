package com.nataliia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DbConnector {
    private static final String dbUr1 = "jdbc:mysql://localhost:3306/natalka_test?serverTimezone=UTC";
    private static final String name = "root";
    private static final String pass = "1111";

    public static Optional<Connection> connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUr1,name,pass);
            return Optional.of(connection);
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
