package com.training.InternetStore.controller.command.adminCommand.productCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class ProductsManagePage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("categories", userService.getAllCategories());
        request.setAttribute("colors", userService.getAllColors());
        request.setAttribute("sizes", userService.getAllSizes());
        request.setAttribute("products", userService.getAllProducts());
        return JSPPageConstants.MANAGE_PRODUCTS_PAGE;
    }
}
