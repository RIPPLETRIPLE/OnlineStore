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
        boolean result = false;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_SIZE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            pstmt.setString(++i, entity.getSize());
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
    public void delete(Product.Size size) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_SIZE)) {
            int i = 0;
            pstmt.setLong(++i, size.getId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            logger.error(throwables.getMessage(), throwables);
        }
    }

    @Override
    public boolean update(Product.Size size) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_SIZE)) {
            int i = 0;
            pstmt.setString(++i, size.getSize());
            pstmt.setLong(++i, size.getId());
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
