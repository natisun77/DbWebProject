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

    public Connection getConnection() {
        return DbConnector.connect().get();
    }

    public boolean addGood(Good good) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO goods(name, description, price) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, good.getName());
            preparedStatement.setString(2, good.getDescription());
            preparedStatement.setDouble(3, good.getPrice());
            logger.debug(sql);
            return 1 == preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add good", e);
            return false;
        }
    }

    public Optional<Good> getGoodById(Long id) {
        try (Connection connection = getConnection()) {
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
        try (Connection connection = getConnection()) {
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

    public boolean updateGood(Good good) {
        try (Connection connection = getConnection()) {
            String sql = "UPDATE goods SET name = ?, description = ?, price = ? WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, good.getName());
            preparedStatement.setString(2, good.getDescription());
            preparedStatement.setDouble(3, good.getPrice());
            preparedStatement.setLong(4, good.getId());
            logger.debug(sql);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't update information of good. ", e);
        }
        return false;
    }

    public boolean deleteGood(long id) {
        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM goods WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            logger.debug(sql);

            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Can't delete good. ", e);
        }
        return false;
    }
}
