package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.dao.mapper.CategoryMapper;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao {
    private final Logger logger = LogManager.getLogger(JDBCCategoryDao.class);

    private final Connection connection;
    private final CategoryMapper categoryMapper = new CategoryMapper();

    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Category entity) {
        return false;
    }

    @Override
    public Optional<Product.Category> findById(int id) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_CATEGORY_BY_ID)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(categoryMapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Category> findAll() {
        List<Product.Category> categories = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(SQLConstants.FIND_ALL_CATEGORIES)
        ) {
            while (resultSet.next()) {
                categories.add(categoryMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return categories;
    }

    @Override
    public void delete(Product.Category id) {

    }

    @Override
    public boolean update(Product.Category entity) {
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
