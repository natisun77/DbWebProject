package com.nataliia.dao;

import com.nataliia.model.Good;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoodDao {
    private static final Logger logger = Logger.getLogger(GoodDao.class);

    public int addGood(Good good) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "INSERT INTO users(name, description, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, good.getName());
            preparedStatement.setString(2, good.getDescription());
            preparedStatement.setDouble(3, good.getPrice());
            int result = preparedStatement.executeUpdate();
            logger.debug(sql);
            return result;
        } catch (SQLException e) {
            logger.error("Can't add a good", e);
            return 0;
        }
    }

    public Optional<Good> getGoodById(Long id) {
        Connection connection = DbConnector.connect().get();
        try {
            String sql = "SELECT * FROM goods WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.debug(sql);
            if (resultSet.next()) {
                Long goodId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                double price = resultSet.getDouble(4);
                Good good = new Good(goodId, name, description, price);
                return Optional.of(good);
            }
        } catch (SQLException e) {
            logger.error("Can't get good by its ID ", e);
        }
        return Optional.empty();
    }

    public List<Good> getGoods() {
        List<Good> goodsList = new ArrayList<>();
        Connection connection = DbConnector.connect().get();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM goods";
            statement.execute(sql);
            ResultSet allGoodsResultSet = statement.getResultSet();
            logger.debug(sql);
            while (allGoodsResultSet.next()) {
                Long goodId = allGoodsResultSet.getLong(1);
                String name = allGoodsResultSet.getString(2);
                String description = allGoodsResultSet.getString(3);
                double price = allGoodsResultSet.getDouble(4);
                Good good = new Good(goodId, name, description, price);
                goodsList.add(good);
            }
        } catch (SQLException e) {
            logger.error("Can't get goods from DB", e);
        }
        return goodsList;
    }
}
