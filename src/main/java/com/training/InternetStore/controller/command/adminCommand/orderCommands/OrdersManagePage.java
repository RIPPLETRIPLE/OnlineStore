package com.training.InternetStore.controller.command.adminCommand.orderCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class OrdersManagePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HashMap<User, List<Order>> userToOrders = new HashMap<>();
        List<User> users = adminService.getAllUsers();

        for (User user : users) {
            userToOrders.put(user, adminService.getAllOrdersForUser(user));
        }

        request.setAttribute("usersToOrders", userToOrders);
        return JSPPageConstants.MANAGE_ORDERS_PAGE;
    }
}
