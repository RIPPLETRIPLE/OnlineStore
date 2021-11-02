package com.training.InternetStore.model.entity;

import com.training.InternetStore.model.entity.enums.OrderStatus;

public class Order {
    private User user;
    private Product product;
    private int quantity;
    private OrderStatus status;

    private Order() {
    }

    private Order(User user, Product product, int quantity, OrderStatus status) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }

    public static Order createOrder(User user, Product product, int quantity, OrderStatus status) {
        return new Order(user, product, quantity, status);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
