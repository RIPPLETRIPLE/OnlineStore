package com.training.InternetStore.controller.command.adminCommand.productCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.adminCommand.orderCommands.DeleteOrder;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DeleteProduct implements Command {
    private final Logger logger = LogManager.getLogger(DeleteProduct.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = userService.getProductById(productId);
            userService.deleteProduct(product);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(),ex);
        }
        return "redirect:/app/admin/productsManagePage";
    }
}
