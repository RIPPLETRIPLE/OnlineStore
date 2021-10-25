package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.model.dao.SizeDao;
import com.training.InternetStore.model.entity.Good;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JDBCSizeDao implements SizeDao {
    private Connection connection;

    public JDBCSizeDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Good.Size entity) {
        return false;
    }

    @Override
    public Optional<Good.Size> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Good.Size> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
