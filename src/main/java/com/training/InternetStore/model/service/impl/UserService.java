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
import java.util.Optional;
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
            User userWithSuchLogin = userDao.findByLogin(user.getLogin()).orElseThrow(FieldDontPresent::new);
            if (user.getPassword().equals(userWithSuchLogin.getPassword())) {
                user.setId(userWithSuchLogin.getId());
                return true;
            }
        } catch (FieldDontPresent e) {
            return false;
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
}
