package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.mapper.UserMapper;
import com.training.InternetStore.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private UserMapper userMapper = new UserMapper();

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(User user) {
        boolean result = false;
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_USER)) {
            int i = 0;

            pstmt.setString(++i, user.getLogin());
            pstmt.setString(++i, user.getPassword());
            pstmt.setString(++i, user.getRole().toString());

            if (pstmt.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_LOGIN)
        ) {
            pstmt.setString(1, login);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(userMapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet resultSet = stmt.executeQuery(SQLConstants.FIND_ALL_USERS)
        ) {
            while (resultSet.next()) {
                users.add(userMapper.extractFromResultSet(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }
}
