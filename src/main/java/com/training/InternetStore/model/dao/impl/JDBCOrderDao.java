package com.training.InternetStore.model.dao.impl;

import com.training.InternetStore.controller.constants.SQLConstants;
import com.training.InternetStore.model.dao.OrderDao;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.mapper.OrderMapper;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
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
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.ADD_ORDER_FOR_USER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            pstmt.setLong(++i, order.getUser().getId());
            pstmt.setLong(++i, order.getProduct().getId());
            pstmt.setString(++i, order.getStatus().toString());
            pstmt.setInt(++i, order.getQuantity());
            if (pstmt.executeUpdate() > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getLong(1));
                    }
                }
            }
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
    public void delete(Order order) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_ORDER)) {
            int i = 0;
            pstmt.setLong(++i, order.getId());
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean update(Order order) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_ORDER)) {
            int i = 0;
            pstmt.setInt(++i, order.getQuantity());
            pstmt.setLong(++i, order.getUser().getId());
            pstmt.setLong(++i, order.getProduct().getId());
            pstmt.setString(++i, order.getStatus().toString());
            pstmt.setLong(++i, order.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
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

    @Override
    public Optional<Order> findOrderForUserByOrderStatusAndProduct(User user, Product product, OrderStatus status) {
        try (PreparedStatement pstmt = connection.prepareStatement(SQLConstants.FIND_ORDER_FOR_USER_ORDER_STATUS_PRODUCT)) {
            pstmt.setLong(1, user.getId());
            pstmt.setLong(2, product.getId());
            pstmt.setString(3, status.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(orderMapper.extractFromResultSet(rs));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (FieldDontPresent fieldDontPresent) {
            return Optional.empty();
        }
        return Optional.empty();
    }
}
