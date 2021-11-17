package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.ProductMapper;
import com.training.InternetStore.model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
        ResultSet rs = null;
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_PRODUCT_BY_ID)
        ) {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(productMapper.extractFromResultSet(rs));
            }
        } catch (SQLException | FieldDontPresent exception) {
            //logger
            return Optional.empty();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    //logger
                }
            }
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
    public boolean update(Product product) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_PRODUCT)) {
            int i = 0;
            pstmt.setString(++i, product.getName());
            pstmt.setString(++i, product.getImg());
            pstmt.setLong(++i, product.getPrice());
            pstmt.setString(++i, product.getSex().toString());
            pstmt.setLong(++i, product.getCategory().getId());
            pstmt.setLong(++i, product.getSize().getId());
            pstmt.setLong(++i, product.getColor().getId());
            pstmt.setLong(++i, product.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public List<Product> findProductWithSortAndLimit(String sortBy, String order, int from, int to) {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        sortBy = " " + SQLConstants.sortBy.getOrDefault(sortBy, "ORDER BY id");
        order = " " + SQLConstants.order.getOrDefault(order, "ASC") + " ";
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ALL_PRODUCTS.replaceAll(";", "") + sortBy + order + SQLConstants.LIMIT)) {
            int i = 0;
            pstmt.setInt(++i, --from);
            pstmt.setInt(++i, to);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                products.add(productMapper.extractFromResultSet(rs));
            }
        } catch (SQLException | FieldDontPresent throwables) {
            throwables.printStackTrace();
            return products;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }

    @Override
    public int findAmountOfProducts() {
        ResultSet rs = null;
        try (Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(SQLConstants.FIND_AMOUNT_OF_PRODUCTS);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            //logger
            return 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }
}
