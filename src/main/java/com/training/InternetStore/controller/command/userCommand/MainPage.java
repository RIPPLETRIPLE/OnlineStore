package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class MainPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        List<Product> products = userService.getAllProducts();

        request.setAttribute("products", products);
        return JSPPageConstants.MAIN_PAGE ;
    }
}
