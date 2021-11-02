package com.training.InternetStore.model.dao.mapper;

import com.training.InternetStore.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper{
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        return User.createUser(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
    }
}
