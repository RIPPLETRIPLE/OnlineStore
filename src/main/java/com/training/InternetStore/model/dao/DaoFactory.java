package com.training.InternetStore.model.dao;

import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;
    protected String resourcePropertiesName = "db";
    public static DaoFactory getInstance(Class<? extends DaoFactory> type) {

        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                try {
                    daoFactory = type.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        return daoFactory;
    }

    public abstract UserDao createUserDao();

    public abstract GoodDao createGoodDao();

    public abstract CategoryDao createCategoryDao();

    public abstract SizeDao createSizeDao();

    public abstract ColorDao createColorDao();


}