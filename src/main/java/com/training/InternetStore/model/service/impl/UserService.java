package com.training.InternetStore.model.service.impl;

import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.OrderDao;
import com.training.InternetStore.model.dao.ProductDao;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.service.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements Service {
    private static UserService userService;

    public static UserService getInstance() {
        if (userService == null) {
            synchronized (UserService.class) {
                userService = new UserService();
            }
        }
        return userService;
    }

    private UserService() {
    }

    public boolean createNewUser(String login, String password) {
        return userDao.create(User.createUser(login, password));
    }

    public boolean DBContainsUser(User user) {
        try {
            user.setId(userDao.findByLogin(user.getLogin()).orElseThrow(FieldDontPresent::new).getId());
        } catch (FieldDontPresent e) {
            return false;
        }
        return true;
    }

    public Product getProductById(int id) throws FieldDontPresent {
        return productDao.findById(id).orElseThrow(FieldDontPresent::new);
    }

    public boolean addProductToUserCart(User user, Product product) {
        Order order = Order.createOrder(user, product, 1, OrderStatus.valueOf("Unregistered"));
        return orderDao.create(order);
    }

    public List<Order> getOrdersByStatus(User user, OrderStatus status) {
        return orderDao.findOrdersForUserByOrderStatus(user, status);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public void updateStatusForOrders(List<Order> orders, OrderStatus status) {
        orders.forEach(e -> {
            e.setStatus(status);
            orderDao.update(e);
        });
    }

    public boolean incrementProductInCart(User user, Product product) {
        try {
            Order order = orderDao.findOrderForUserByOrderStatusAndProduct(user, product, OrderStatus.Unregistered).orElseThrow(FieldDontPresent::new);
            order.setQuantity(order.getQuantity() + 1);
            orderDao.update(order);
        } catch (FieldDontPresent e) {
            return false;
        }
        return false;
    }

    public boolean decrementProductInCart(User user, Product product) {
        try {
            Order order = orderDao.findOrderForUserByOrderStatusAndProduct(user, product, OrderStatus.Unregistered).orElseThrow(FieldDontPresent::new);
            if (order.getQuantity() - 1 > 0) {
                order.setQuantity(order.getQuantity() - 1);
                orderDao.update(order);
            }
        } catch (FieldDontPresent e) {
            return false;
        }
        return false;
    }

    public boolean deleteOrderFromCart(User user, Product product) {
        try {
            Order order = orderDao.findOrderForUserByOrderStatusAndProduct(user, product, OrderStatus.Unregistered).orElseThrow(FieldDontPresent::new);
            order.setQuantity(order.getQuantity() - 1);
            orderDao.delete(order);
        } catch (FieldDontPresent e) {
            return false;
        }
        return false;
    }
}
