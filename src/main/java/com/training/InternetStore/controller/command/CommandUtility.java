package com.training.InternetStore.controller.command;


import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.HashSet;

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

    public static void addProductToCartForUnloggedUser(HttpSession session, Product product) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Product, Integer>());
        }

        HashMap<Product, Integer> cart = (HashMap<Product, Integer>) session.getAttribute("cart");

        cart.putIfAbsent(product, 0);
        cart.put(product, cart.get(product) + 1);

        session.setAttribute("cart", cart);
    }
}
