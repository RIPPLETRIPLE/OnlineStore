package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private DataSource dataSource;
    Connection connection = getConnection();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(connection);
    }

    @Override
    public ProductDao createProductDao() {
        return new JDBCProductDao(connection);
    }

    @Override
    public CategoryDao createCategoryDao() {
        return new JDBCCategoryDao(connection);
    }

    @Override
    public SizeDao createSizeDao() {
        return new JDBCSizeDao(connection);
    }

    @Override
    public ColorDao createColorDao() {
        return new JDBCColorDao(connection);
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(connection);
    }

    private Connection getConnection() {
        dataSource = ConnectionPoolHolder.getDataSource(resourcePropertiesName);
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
