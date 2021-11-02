package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import java.util.List;

public interface OrderDao extends GenericDao<Order>{
    public List<Order> findOrdersForUserByOrderStatus(User user, OrderStatus status);
}
