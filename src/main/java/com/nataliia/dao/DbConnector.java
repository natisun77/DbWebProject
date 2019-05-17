package com.nataliia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DbConnector {
    private static final String DB_UR_1 = "jdbc:mysql://localhost:3306/natalka_test?serverTimezone=UTC&useSSL=false";
    private static final String NAME = "root";
    private static final String PASS = "1111";

    public static Optional<Connection> connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_UR_1, NAME, PASS);
            return Optional.of(connection);
        } catch (ClassNotFoundException | SQLException  e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
