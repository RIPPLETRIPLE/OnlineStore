package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.CategoryDao;
import com.training.InternetStore.model.entity.Good;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCCategoryDao implements CategoryDao {
    private Connection connection;

    public JDBCCategoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Good.Category entity) {
        return false;
    }

    @Override
    public Optional<Good.Category> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Good.Category> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
