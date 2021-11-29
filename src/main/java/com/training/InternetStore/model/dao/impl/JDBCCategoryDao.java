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
        boolean result = false;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_CATEGORY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            pstmt.setString(++i, entity.getName());
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
    public void delete(Product.Category category) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_CATEGORY)) {
            int i = 0;
            pstmt.setLong(++i, category.getId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
    }

    @Override
    public boolean update(Product.Category entity) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_CATEGORY)) {
            int i = 0;
            pstmt.setString(++i, entity.getName());
            pstmt.setLong(++i, entity.getId());
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
