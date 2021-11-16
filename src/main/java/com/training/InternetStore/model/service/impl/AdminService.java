package com.training.InternetStore.model.service.impl;

import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
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

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public Optional<User> getUserByID(int userId) {
        return userDao.findById(userId);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public List<Product> getProductWithSortAndLimit(String sortBy, String method, int from, int to) {
        return productDao.findProductWithSortAndLimit(sortBy, method, from, to);
    }

    public int getAmountOfProducts() {
        return productDao.findAmountOfProducts();
    }
}
