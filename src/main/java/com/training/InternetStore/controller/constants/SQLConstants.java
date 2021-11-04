package com.training.InternetStore.controller.constants;

public class SQLConstants {



    public static String CREATE_NEW_USER = "INSERT INTO users (login, password, role) VALUES(?, ?, ?)";
    public static String FIND_ALL_USERS = "SELECT * FROM users";
    public static String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
    public static String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = (?)";
    public static String FIND_ALL_PRODUCTS = "SELECT * FROM products";
    public static String FIND_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = (?)";
    public static String FIND_ALL_CATEGORIES = "SELECT * FROM category";
    public static String FIND_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = (?)";
    public static String FIND_ALL_COLORS = "SELECT * FROM color";
    public static String FIND_COLOR_BY_ID = "SELECT * FROM color WHERE id = (?)";
    public static String FIND_ALL_SIZES = "SELECT * FROM size";
    public static String FIND_SIZE_BY_ID = "SELECT * FROM size WHERE id = (?)";
    public static String ADD_ORDER_FOR_USER = "INSERT INTO orders (user_ID, product_ID, status, quantity) VALUES (?, ?, ?, ?)";
    public static String FIND_ORDERS_FOR_USER_ORDER_STATUS = "SELECT * FROM orders WHERE user_ID = (?) AND status = (?)";
    public static  String FIND_ORDER_FOR_USER_ORDER_STATUS_PRODUCT = "SELECT * FROM orders WHERE user_ID = (?) AND product_ID = (?) AND status = (?);";
    public static final String UPDATE_ORDER = "UPDATE orders SET quantity = (?) WHERE user_ID = (?) AND product_ID = (?) AND status = (?)";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE user_ID = (?) AND product_ID = (?) AND status = (?) ";
}
