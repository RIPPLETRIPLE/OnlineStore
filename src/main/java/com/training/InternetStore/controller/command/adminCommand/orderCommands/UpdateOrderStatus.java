package com.training.InternetStore.controller.command.adminCommand.orderCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateOrderStatus implements Command {
    private final Logger logger = LogManager.getLogger(UpdateOrderStatus.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Order order = adminService.getOrderById(orderId).orElseThrow(FieldDontPresent::new);
            order.setStatus(OrderStatus.valueOf(request.getParameter("status")));
            adminService.updateOrder(order);
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return "redirect:/app/admin/ordersManagePage";
        }

        return "redirect:/app/admin/ordersManagePage";
    }
}
