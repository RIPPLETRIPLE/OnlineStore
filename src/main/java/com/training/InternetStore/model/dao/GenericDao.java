package com.training.InternetStore.model.dao;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    boolean create(T entity);

    Optional<T> findById(int id);

    List<T> findAll();

    void delete(int id);

    void close();
}
