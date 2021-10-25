package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.GoodDao;
import com.training.InternetStore.model.entity.Good;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCGoodDao implements GoodDao {
    private Connection connection;

    public JDBCGoodDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Good entity) {
        return false;
    }

    @Override
    public Optional<Good> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Good> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
