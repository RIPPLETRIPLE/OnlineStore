package com.training.InternetStore.controller.command.adminCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DeleteProduct implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = userService.getProductById(productId);
            userService.deleteProduct(product);
        } catch (Exception ex) {
            return JSPPageConstants.MANAGE_PRODUCTS_PAGE;
        }
        return "redirect:/app/admin/productsManagePage";
    }
}
