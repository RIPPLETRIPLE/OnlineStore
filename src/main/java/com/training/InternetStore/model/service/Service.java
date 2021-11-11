package com.training.InternetStore.model.service;

import com.training.InternetStore.model.dao.*;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;

public interface Service {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);

    CategoryDao categoryDao = daoFactory.createCategoryDao();
    UserDao userDao = daoFactory.createUserDao();
    OrderDao orderDao = daoFactory.createOrderDao();
    SizeDao sizeDao = daoFactory.createSizeDao();
    ColorDao colorDao = daoFactory.createColorDao();
    ProductDao productDao = daoFactory.createProductDao();
}
