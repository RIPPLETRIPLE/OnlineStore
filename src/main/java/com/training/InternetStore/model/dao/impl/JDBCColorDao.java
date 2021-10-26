package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.ColorDao;
import com.training.InternetStore.model.entity.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCColorDao implements ColorDao {
    private Connection connection;

    public JDBCColorDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Color entity) {
        return false;
    }

    @Override
    public Optional<Product.Color> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Product.Color> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}