package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CancelRegisteredOrder implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int orderId;

        try {
            orderId = Integer.parseInt(request.getParameter("orderId"));
        } catch (NumberFormatException ex) {
            return "redirect:" + "/app/user/ordersPage";
        }
        Order order;

        try {
            order = userService.getOrderByID(orderId).orElseThrow(FieldDontPresent::new);
        } catch (FieldDontPresent e) {
            return "redirect:" + "/app/user/ordersPage";
        }
        userService.deleteOrder(order);

        return "redirect:" + "/app/user/ordersPage";
    }
}
