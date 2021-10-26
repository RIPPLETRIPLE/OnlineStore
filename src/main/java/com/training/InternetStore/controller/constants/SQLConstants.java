package com.training.InternetStore.controller.constants;

public class SQLConstants {
    public static String CREATE_NEW_USER = "INSERT INTO users (login, password, role) VALUES(?, ?, ?)";
    public static String FIND_ALL_USERS = "SELECT * FROM users";
    public static String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
    public static String FIND_ALL_PRODUCTS = "SELECT * FROM products";
    public static String FIND_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = ?";
}
