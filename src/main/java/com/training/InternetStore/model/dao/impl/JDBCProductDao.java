package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.ProductMapper;
import com.training.InternetStore.model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCProductDao implements ProductDao {
    private final Connection connection;
    private final ProductMapper productMapper = new ProductMapper();

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product entity) {
        boolean result = false;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            pstmt.setString(++i, entity.getName());
            pstmt.setString(++i, entity.getImg());
            pstmt.setInt(++i, entity.getPrice());
            pstmt.setString(++i, entity.getSex().toString());
            pstmt.setLong(++i, entity.getCategory().getId());
            pstmt.setLong(++i, entity.getColor().getId());
            pstmt.setLong(++i, entity.getSize().getId());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
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
        } catch (FieldDontPresent fieldDontPresent) {
            return Optional.empty();
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
                try {
                    Product product = productMapper.extractFromResultSet(resultSet);
                    products.add(product);
                } catch (FieldDontPresent fieldDontPresent) {
                    return products;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public void delete(Product product) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_PRODUCT)) {
            int i = 0;
            pstmt.setLong(++i, product.getId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean update(Product entity) {
        return false;
    }

    @Override
    public void close() {

    }
}
