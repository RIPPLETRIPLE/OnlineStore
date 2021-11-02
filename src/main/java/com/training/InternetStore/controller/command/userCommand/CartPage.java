package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class CartPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;

        if (user != null) {
            List<Order> orders = userService.getCartForUser(user);
            request.setAttribute("cart", userService.getCartForUser(user));
        }
        return JSPPageConstants.CART_PAGE;
    }
}
