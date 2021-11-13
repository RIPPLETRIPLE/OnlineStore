package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends GenericDao<Order>{
    public List<Order> findOrdersForUserByOrderStatus(User user, OrderStatus status);

    Optional<Order> findOrderForUserByOrderStatusAndProduct(User user, Product product, OrderStatus unregistered);

    List<Order> findAllOrdersForUser(User user);
}
