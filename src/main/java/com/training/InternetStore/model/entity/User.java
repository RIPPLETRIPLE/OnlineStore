package com.training.InternetStore.model.entity;

public class User {
    private long id;
    private String login;
    private String password;
    private Role role;
    private UserStatus status;

    private User(String login, String password, Role role, UserStatus status) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public static User createUser(String login, String password, UserStatus status) {
        return new User(login, password, login.equals("Admin") ? Role.Admin : Role.User, status);
    }

    public static User createUser(long id, String login, String password, UserStatus status) {
        User user = new User(login, password, login.equals("Admin") ? Role.Admin : Role.User, status);
        user.setId(id);
        return user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return login.equals(((User) obj).login) && password.equals(((User) obj).password);
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }



    public enum Role {
        Admin, User, Guest
    }
    public enum UserStatus {
        Blocked, Unblocked;
    }
}
