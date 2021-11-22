package com.training.InternetStore.controller.command.adminCommand.orderCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DeleteOrder implements Command {
    private final Logger logger = LogManager.getLogger(DeleteOrder.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            userService.deleteOrder(adminService.getOrderById(orderId).orElseThrow(FieldDontPresent::new));
        } catch (Exception fieldDontPresent) {
            logger.warn(fieldDontPresent.getMessage(), fieldDontPresent);
            return "redirect:/app/admin/ordersManagePage";
        }
        return "redirect:/app/admin/ordersManagePage";
    }
}
