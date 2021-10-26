package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.SizeDao;
import com.training.InternetStore.model.entity.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCSizeDao implements SizeDao {
    private Connection connection;

    public JDBCSizeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Product.Size entity) {
        return false;
    }

    @Override
    public Optional<Product.Size> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Product.Size> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
