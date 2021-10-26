package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.ProductDao;
import com.training.InternetStore.model.dao.mapper.ProductMapper;
import com.training.InternetStore.model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCProductDao implements ProductDao {
    private Connection connection;
    private ProductMapper productMapper = new ProductMapper();

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product entity) {
        return false;
    }

    @Override
    public Optional<Product> findById(int id) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_PRODUCT_BY_ID)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(productMapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(SQLConstants.FIND_ALL_PRODUCTS)
        ) {
            while (resultSet.next()) {
                products.add(productMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
