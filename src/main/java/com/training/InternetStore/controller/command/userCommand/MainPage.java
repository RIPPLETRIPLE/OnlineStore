package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.SortFilterUtility;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class MainPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        List<Product> products = userService.getAllProducts();
        if (sortBy != null) {
            SortFilterUtility.sortListBy(sortBy, products);
        }
        request.setAttribute("products", products);
        return JSPPageConstants.MAIN_PAGE ;
    }
}
