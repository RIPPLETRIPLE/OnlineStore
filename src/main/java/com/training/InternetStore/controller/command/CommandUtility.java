package com.training.InternetStore.controller.command;


import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CommandUtility {
    public static boolean checkUserIsLogged(HttpSession session, User user) {
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        if (loggedUsers.stream().anyMatch(userLogin::equals)) {
            return true;
        }
        loggedUsers.add(userLogin);
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static void deleteUserFromLogged(HttpSession session) {
        User user = (User) session.getAttribute("user");
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        loggedUsers.removeIf(uL -> uL.equals(userLogin));
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        session.removeAttribute("user");
    }

    public static void addProductToCartForUnloggedUser(HttpSession session, Product product, int quantity) {
        List<Order> cart = getCart(session);

            if (cart.stream().anyMatch((c) -> c.getProduct().equals(product))) {
                cart.forEach(order -> {
                    if (order.getQuantity() + quantity > 0 && order.getProduct().equals(product)) {
                        order.setQuantity(order.getQuantity() + quantity);
                    }
                });
            } else {
                cart.add(Order.createOrder(null, product, quantity, OrderStatus.Unregistered));
            }

        session.setAttribute("cart", cart);
    }

    public static void removeProductFromCardForUnloggedUser(HttpSession session, Product product) {
        List<Order> cart = getCart(session);

        cart = cart.stream().filter(c -> !c.getProduct().equals(product)).collect(Collectors.toList());

        session.setAttribute("cart", cart);
    }


    private static List<Order> getCart(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<Order>());
        }

        return (List<Order>) session.getAttribute("cart");
    }
}
