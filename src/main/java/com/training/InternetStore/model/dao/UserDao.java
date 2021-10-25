package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByLogin(String login);
}
