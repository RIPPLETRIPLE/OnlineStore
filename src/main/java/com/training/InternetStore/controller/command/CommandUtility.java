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

    public static void addProductToCartForUnloggedUser(HttpSession session, Product product, int quantity) {
        HashMap<Product, Integer> cart = getCart(session);
        if (!cart.containsKey(product)) {
            cart.put(product, 1);
        } else {
            int currentQuantity = cart.get(product);

            if (currentQuantity + quantity > 0) {
                cart.put(product, currentQuantity + quantity);
            }
        }
        session.setAttribute("cart", cart);
    }

    public static void removeProductFromCardForUnloggedUser(HttpSession session, Product product) {
        HashMap<Product, Integer> cart = getCart(session);

        cart.remove(product);

        session.setAttribute("cart", cart);
    }


    private static HashMap<Product, Integer> getCart(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new HashMap<Product, Integer>());
        }

        return (HashMap<Product, Integer>) session.getAttribute("cart");
    }
}
