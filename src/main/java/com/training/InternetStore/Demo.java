package com.training.InternetStore;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;

import java.util.ArrayList;
import java.util.List;


public class Demo {
    public static void main(String[] args) throws FieldDontPresent {
        DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);
        daoFactory.createProductDao().findAll().forEach((e) -> {
            System.out.println(e);
        });
        List<Order> orders = new ArrayList<>();

    }
}
