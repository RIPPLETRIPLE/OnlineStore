package com.training.InternetStore.controller.constants;

public class SQLConstants {
    public static String CREATE_NEW_USER = "INSERT INTO users (login, password, role) VALUES(?, ?, ?)";
    public static String FIND_ALL_USERS = "SELECT * FROM users";
    public static String FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
}
