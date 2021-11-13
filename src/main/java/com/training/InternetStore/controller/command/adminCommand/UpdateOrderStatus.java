package com.training.InternetStore.controller.command.adminCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateOrderStatus implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = adminService.getOrderById(orderId).orElseThrow(FieldDontPresent::new);
            order.setStatus(OrderStatus.valueOf(request.getParameter("status")));
            adminService.updateOrder(order);
        } catch (Exception e) {
            return "redirect:/app/admin/ordersManagePage";
        }

        return "redirect:/app/admin/ordersManagePage";
    }
}
