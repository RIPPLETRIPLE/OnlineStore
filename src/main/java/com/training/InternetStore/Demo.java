package com.training.InternetStore;
import com.training.InternetStore.model.dao.DaoFactory;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCDaoFactory;
import com.training.InternetStore.model.entity.Product;


public class Demo {
    public static void main(String[] args) throws FieldDontPresent {
        DaoFactory daoFactory = DaoFactory.getInstance(JDBCDaoFactory.class);
        Product product = daoFactory.createProductDao().findById(1).get();
        System.out.println(product);
    }
}
