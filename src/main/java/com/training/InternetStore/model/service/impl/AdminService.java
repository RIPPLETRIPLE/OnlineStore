package com.training.InternetStore.model.service.impl;

import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.service.Service;

import java.util.List;
import java.util.Optional;

public class AdminService implements Service {
    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null) {
            synchronized (UserService.class) {
                adminService = new AdminService();
            }
        }
        return adminService;
    }

    private AdminService() {
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public List<Order> getAllOrdersForUser(User user) {
        return orderDao.findAllOrdersForUser(user);
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderDao.findById(orderId);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }
}
