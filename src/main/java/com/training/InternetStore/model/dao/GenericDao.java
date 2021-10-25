package com.training.InternetStore.model.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> extends AutoCloseable {
    boolean create(T entity);

    Optional<T> findById(int id);

    List<T> findAll();

    void delete(int id);

    void close();
}
