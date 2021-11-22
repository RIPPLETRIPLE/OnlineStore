package com.training.InternetStore.model.service.impl;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.service.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserService implements Service {
    private final Logger logger = LogManager.getLogger(UserService.class);
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

    public boolean createNewUser(User user) {
        return userDao.create(user);
    }

    public boolean DBContainsUser(User user) {
        try {
            User userWithSuchLogin = userDao.findByLogin(user.getLogin()).orElseThrow(FieldDontPresent::new);
            if (user.getPassword().equals(userWithSuchLogin.getPassword())) {
                user.setStatus(userWithSuchLogin.getStatus());
                user.setId(userWithSuchLogin.getId());
                user.setRole(userWithSuchLogin.getRole());
                return true;
            }
        } catch (FieldDontPresent e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public Product getProductById(int id) throws FieldDontPresent {
        return productDao.findById(id).orElseThrow(FieldDontPresent::new);
    }

    public boolean createOrder(Order order) {
        return orderDao.create(order);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Optional<Order> getOrderByID(int id) {
        return orderDao.findById(id);
    }

    public List<Order> getOrdersByStatus(User user, OrderStatus status) {
        return orderDao.findOrdersForUserByOrderStatus(user, status);
    }

    public void updateStatusForOrders(List<Order> orders, OrderStatus status) {
        orders.forEach(e -> {
            e.setStatus(status);
            orderDao.update(e);
        });
    }

    public boolean incrementOrderQuantity(Order order) {
        order.setQuantity(order.getQuantity() + 1);
        return orderDao.update(order);
    }


    public boolean decrementOrderQuantity(Order order) {
        if (order.getQuantity() - 1 > 0) {
            order.setQuantity(order.getQuantity() - 1);
            return orderDao.update(order);
        }
        return false;
    }

    public boolean deleteOrder(Order order) {
        order.setQuantity(order.getQuantity() - 1);
        orderDao.delete(order);
        return true;
    }

    public void retainCartForLoggedUser(List<Order> cart, User user) {
        cart.forEach(e -> {
            e.setUser(user);
            userService.createOrder(e);
        });
    }

    public List<Product.Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public List<Product.Size> getAllSizes() {
        return sizeDao.findAll();
    }

    public List<Product.Color> getAllColors() {
        return colorDao.findAll();
    }

    public Product.Category getCategoryByID(int categoryId) throws FieldDontPresent {
        return categoryDao.findById(categoryId).orElseThrow(FieldDontPresent::new);
    }

    public Product.Color getColorByID(int colorId) throws FieldDontPresent {
        return colorDao.findById(colorId).orElseThrow(FieldDontPresent::new);
    }

    public Product.Size getSizeByID(int sizeId) throws FieldDontPresent {
        return sizeDao.findById(sizeId).orElseThrow(FieldDontPresent::new);
    }

    public void createProduct(Product product) {
        productDao.create(product);
    }

    public void deleteProduct(Product product) {
         productDao.delete(product);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
