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
        return false;
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
    public void delete(Product.Color id) {

    }

    @Override
    public boolean update(Product.Color entity) {
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
