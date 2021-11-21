package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.ProductMapper;
import com.training.InternetStore.model.entity.Product;

import java.sql.*;
import java.util.*;

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
    public List<Product> findProductWithSortLimitAndFilter(String sortBy, String order, String[] filterParams, int from, int to) {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        StringBuilder fullStatement = new StringBuilder();
        fullStatement.append(SQLConstants.FIND_ALL_PRODUCTS.replaceAll(";", ""));

        addFilterToQuery(filterParams, fullStatement);

        fullStatement
                .append(" ").append(SQLConstants.sortBy.getOrDefault(sortBy, "ORDER BY id"))
                .append(" ").append(SQLConstants.order.getOrDefault(order, "ASC"))
                .append(" ").append(SQLConstants.LIMIT).append(";");

        if (    filterParams.length > 0 && (!fullStatement.toString().contains("category_ID")
                || !fullStatement.toString().contains("color_ID")
                || !fullStatement.toString().contains("size_ID")
                || !fullStatement.toString().contains("sex"))) {
            return products;
        }
        try (PreparedStatement pstmt = connection.prepareStatement(fullStatement.toString().replaceAll(" OR \\)", ")").replaceAll(" AND  ", " "))) {
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
    public int findAmountOfProductsWithFilter(String[] filterParams) {
        StringBuilder statementWithFilter = new StringBuilder(SQLConstants.FIND_AMOUNT_OF_PRODUCTS);
        addFilterToQuery(filterParams, statementWithFilter);
        statementWithFilter.append(";");
        ResultSet rs = null;

        try (Statement statement = connection.createStatement()) {
            rs = statement.executeQuery(statementWithFilter.toString().replaceAll(" OR \\)", ")").replaceAll(" AND ;", " "));
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

    private void addFilterToQuery(String[] filterParams, StringBuilder statementWithFilter) {
        if (filterParams.length > 0) {
            statementWithFilter.append(" WHERE ");
            List<String> strings = Arrays.asList(filterParams);

            HashMap<String, Set<String>> paramNameToValues = new HashMap<>();

            for (String s : filterParams) {
                String[] paramToValues = s.split("=");
                if (paramNameToValues.containsKey(paramToValues[0])) {
                    paramNameToValues.get(paramToValues[0]).add(paramToValues[1]);
                } else {
                    HashSet<String> set = new HashSet<>();
                    set.add(paramToValues[1]);
                    paramNameToValues.put(paramToValues[0], set);
                }
            }

            for (Map.Entry<String, Set<String>> entry : paramNameToValues.entrySet()) {
                statementWithFilter.append("(");
                for (String s : entry.getValue()) {
                    statementWithFilter.append(entry.getKey()).append("=").append(s).append(" OR ");
                }
                statementWithFilter.append(")");
                statementWithFilter.append(" AND ");
            }
        }

    }
}
