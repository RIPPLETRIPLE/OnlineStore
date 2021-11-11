package com.training.InternetStore.controller.command.adminCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AddProductPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        request.setAttribute("categories", userService.getAllCategories());
        request.setAttribute("colors", userService.getAllColors());
        request.setAttribute("sizes", userService.getAllSizes());
        return JSPPageConstants.ADD_PRODUCT_PAGE;
    }
}
