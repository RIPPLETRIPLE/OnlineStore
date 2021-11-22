package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.SizeDao;
import com.training.InternetStore.model.dao.mapper.SizeMapper;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCSizeDao implements SizeDao {
    private final Logger logger = LogManager.getLogger(JDBCSizeDao.class);

    private final Connection connection;
    private final SizeMapper sizeMapper = new SizeMapper();

    public JDBCSizeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Size entity) {
        return false;
    }

    @Override
    public Optional<Product.Size> findById(int id) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_SIZE_BY_ID)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(sizeMapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return Optional.empty();
    }

    @Override
    public List<Product.Size> findAll() {
        List<Product.Size> sizes = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(SQLConstants.FIND_ALL_SIZES)
        ) {
            while (resultSet.next()) {
                sizes.add(sizeMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
        return sizes;
    }

    @Override
    public void delete(Product.Size id) {

    }

    @Override
    public boolean update(Product.Size entity) {
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
