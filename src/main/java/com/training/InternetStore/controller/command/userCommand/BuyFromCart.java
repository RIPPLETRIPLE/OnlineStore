package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuyFromCart implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        User.Role role = user != null ? user.getRole() : User.Role.Guest;


        if (request.getSession().getAttribute("user") == null) {
            return "redirect:" + "/app/guest/login";
        }

        List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
        if (orders.size() > 0) {
            userService.updateStatusForOrders(orders, OrderStatus.Registered);
        }

        return "redirect:" + "/app/" + role.toString().toLowerCase(Locale.ROOT) + "/cartPage";
    }
}
