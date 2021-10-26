package com.training.InternetStore.model.service.impl;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.service.Service;

import java.util.List;

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

    public boolean DBContainsUser(String login, String password) {
        User user = User.createUser(login, password);
        return userDao.findAll().contains(user);
    }

    public Product getProductById(int id) throws FieldDontPresent {
        return productDao.findById(id).orElseThrow(FieldDontPresent::new);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }
}
