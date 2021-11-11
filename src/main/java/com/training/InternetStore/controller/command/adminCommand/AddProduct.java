package com.training.InternetStore.controller.command.adminCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AddProduct implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        Product product;
        try {
            product = CommandUtility.extractProductFromForm(request, userService).orElseThrow(FieldDontPresent::new);
        } catch (FieldDontPresent e) {
            return "redirect:/app/admin/addProductPage";
        }
        userService.createProduct(product);
        return "redirect:/app/admin/addProductPage";
    }
}
