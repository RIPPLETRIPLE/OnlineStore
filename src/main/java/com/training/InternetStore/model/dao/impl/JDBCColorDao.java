package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.ColorDao;
import com.training.InternetStore.model.dao.mapper.ColorMapper;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCColorDao implements ColorDao {
    private final Logger logger = LogManager.getLogger(JDBCColorDao.class);
    private final Connection connection;
    private final ColorMapper colorMapper = new ColorMapper();

    public JDBCColorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Color entity) {
        boolean result = false;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_COLOR, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            pstmt.setString(++i, entity.getColor());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return result;
    }

    @Override
    public Optional<Product.Color> findById(int id) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_COLOR_BY_ID)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(colorMapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Color> findAll() {
        List<Product.Color> colors = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(SQLConstants.FIND_ALL_COLORS)
        ) {
            while (resultSet.next()) {
                colors.add(colorMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return colors;
    }

    @Override
    public void delete(Product.Color color) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_COLOR)) {
            int i = 0;
            pstmt.setLong(++i, color.getId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
    }

    @Override
    public boolean update(Product.Color color) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_COLOR)) {
            int i = 0;
            pstmt.setString(++i, color.getColor());
            pstmt.setLong(++i, color.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return false;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
