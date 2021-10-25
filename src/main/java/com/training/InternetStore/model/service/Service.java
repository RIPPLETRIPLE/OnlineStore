package com.training.InternetStore.model.service;

import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.UserDao;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;

public interface Service {
    DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);
    UserDao userDao = daoFactory.createUserDao();
}
