package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrdersPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        List<Order> orders = new ArrayList<>();
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus != OrderStatus.Unregistered) {
                orders.addAll(userService.getOrdersByStatus(user, orderStatus));
            }
        }
        request.setAttribute("orders", orders);
        return JSPPageConstants.ORDERS_PAGE;
    }
}
