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

public class BuyFromCart implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:" + "/app/login";
        }
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
        if (orders.size() > 0) {
            userService.updateStatusForOrders(orders, OrderStatus.Registered);
        }

        return "redirect:" + "/app/cartPage";
    }
}
