package com.training.InternetStore;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.service.impl.UserService;

public class Demo {
    public static void main(String[] args) throws FieldDontPresent {
        UserService us = UserService.getInstance();
        System.out.println(us.getProductById(1));
    }
}
