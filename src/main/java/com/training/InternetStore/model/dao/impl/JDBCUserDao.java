package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.UserMapper;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import com.training.InternetStore.model.entity.Product;
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
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.CREATE_NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            pstmt.setString(++i, user.getLogin());
            pstmt.setString(++i, user.getPassword());
            pstmt.setString(++i, user.getRole().toString());

            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setId(rs.getLong(1));
                    }
                    result = true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Optional<User> findById(int id) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_ID)
        ) {
            pstmt.setInt(1, id);
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
    public Optional<User> findByLogin(String login) {
        try (
                PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_USER_BY_LOGIN, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, login);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(userMapper.extractFromResultSet(rs));
                }
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
    public void delete(User id) {

    }

    @Override
    public boolean update(User user) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_USER)) {
            int i = 0;
            pstmt.setString(++i, user.getLogin());
            pstmt.setString(++i, user.getPassword());
            pstmt.setString(++i, user.getRole().toString());
            pstmt.setString(++i, user.getStatus().toString());
            pstmt.setLong(++i, user.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public void close() {

    }
}
