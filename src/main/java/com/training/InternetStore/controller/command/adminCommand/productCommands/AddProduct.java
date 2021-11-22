package com.training.InternetStore.controller.command.adminCommand.productCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.util.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class AddProduct implements Command {
    private final Logger logger = LogManager.getLogger(AddProduct.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        Product product;
        try {
            product = CommandUtility.extractProductFromForm(request, userService).orElseThrow(FieldDontPresent::new);
        } catch (FieldDontPresent e) {
            logger.warn(e.getMessage(), e);
            return "redirect:/app/admin/productsManagePage";
        }
        userService.createProduct(product);
        return "redirect:/app/admin/productsManagePage";
    }
}
