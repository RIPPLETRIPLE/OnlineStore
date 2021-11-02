package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.OrderDao;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.ColorMapper;
import com.training.InternetStore.model.dao.mapper.OrderMapper;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOrderDao implements OrderDao {
    private final Connection connection;
    private final OrderMapper orderMapper = new OrderMapper();

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Order order) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_ORDER_FOR_USER)) {
            int i = 0;
            pstmt.setLong(++i, order.getUser().getId());
            pstmt.setLong(++i, order.getProduct().getId());
            pstmt.setString(++i, order.getStatus().toString());
            pstmt.setInt(++i, order.getQuantity());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {

    }

    @Override
    public List<Order> findOrdersForUserByOrderStatus(User user, OrderStatus status) {
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ORDERS_FOR_USER_ORDER_STATUS)) {
            pstmt.setLong(1, user.getId());
            pstmt.setString(2, status.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(orderMapper.extractFromResultSet(rs));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FieldDontPresent fieldDontPresent) {
            return orders;
        }
        return orders;
    }
}
