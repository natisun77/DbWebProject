package com.nataliia.dao.jbdc;

import com.nataliia.model.Code;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeDaoJBDC {
    private static final Logger LOGGER = Logger.getLogger(GoodDaoJBDC.class);

    public Connection getConnection() {
        return DbConnector.connect().get();
    }

    public boolean addCode(Code code) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO confirmation_code(value_code, user_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code.getValue());
            preparedStatement.setLong(2, code.getUserId());

            preparedStatement.executeUpdate();
            LOGGER.debug(sql);
            return true;
        } catch (SQLException e) {
            LOGGER.error("Can't add a code", e);
            return false;
        }
    }

    public boolean isValidCode(String value, long userId, long goodId) {
        try (Connection connection = getConnection()) {
            String sql = "SELECT EXISTS(SELECT * FROM confirmation_code WHERE user_id = ? and good_id = ? and value_code = ? " +
                    " and timestampdiff (minute, creation_date, now()) <1)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, goodId);
            preparedStatement.setString(3, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            LOGGER.debug(sql);
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            LOGGER.error("isValidCode sql failed ", e);
        }
        return false;
    }
}
