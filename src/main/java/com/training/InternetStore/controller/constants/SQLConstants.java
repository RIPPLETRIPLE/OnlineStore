package com.training.InternetStore.controller.constants;

public class SQLConstants {
    public static final String CREATE_NEW_USER = "INSERT INTO users (login, password, role) VALUES(?, ?, ?)";
    public static final String FIND_ALL_USERS = "SELECT * FROM users";
    public static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id = (?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = (?)";
    public static final String FIND_ALL_PRODUCTS = "SELECT * FROM products";
    public static final String FIND_PRODUCT_BY_ID = "SELECT * FROM products WHERE id = (?)";
    public static final String FIND_ALL_CATEGORIES = "SELECT * FROM category";
    public static final String FIND_CATEGORY_BY_ID = "SELECT * FROM category WHERE id = (?)";
    public static final String FIND_ALL_COLORS = "SELECT * FROM color";
    public static final String FIND_COLOR_BY_ID = "SELECT * FROM color WHERE id = (?)";
    public static final String FIND_ALL_SIZES = "SELECT * FROM size";
    public static final String FIND_SIZE_BY_ID = "SELECT * FROM size WHERE id = (?)";
    public static final String ADD_ORDER_FOR_USER = "INSERT INTO orders (user_ID, product_ID, status, quantity) VALUES (?, ?, ?, ?)";
    public static final String FIND_ORDERS_FOR_USER_ORDER_STATUS = "SELECT * FROM orders WHERE user_ID = (?) AND status = (?)";
    public static final String FIND_ORDER_FOR_USER_ORDER_STATUS_PRODUCT = "SELECT * FROM orders WHERE user_ID = (?) AND product_ID = (?) AND status = (?);";
    public static final String UPDATE_ORDER = "UPDATE orders SET quantity = (?), user_ID = (?), product_ID = (?), status = (?) WHERE ID = (?)";
    public static final String DELETE_ORDER = "DELETE FROM orders WHERE ID = (?) ";
    public static final String FIND_ORDER_BY_ID = "SELECT * FROM orders WHERE id = (?)";
    public static final String CREATE_PRODUCT = "INSERT INTO products (name, image, price , sex, category_ID, color_ID, size_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = (?);";
    public static final String FIND_ALL_ORDERS_FOR_USER = "SELECT * FROM orders WHERE user_ID = (?);";
    public static final String UPDATE_PRODUCT = "UPDATE products SET name = (?), image = (?), price = (?), sex = (?), category_ID = (?), size_ID = (?), color_ID = (?) WHERE ID = (?);";
}
