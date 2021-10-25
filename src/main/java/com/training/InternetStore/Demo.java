package com.training.InternetStore;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.service.impl.UserService;

public class Demo {
    public static void main(String[] args) {
        UserService us = UserService.getInstance();
        DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);
        System.out.println(daoFactory.createUserDao().findByLogin("Admin1"));
    }
}
