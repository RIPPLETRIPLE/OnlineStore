package com.training.InternetStore.model.entity;

public class User {
    private long id;
    private String login;
    private String password;
    private Role role;

    private User(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public static User createUser(String login, String password) {
        return new User(login, password, login.equals("Admin") ? Role.Admin : Role.User);
    }

    public static User createUser(long id, String login, String password) {
        User user = new User(login, password, login.equals("Admin") ? Role.Admin : Role.User);
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
        Admin, User
    }
}
