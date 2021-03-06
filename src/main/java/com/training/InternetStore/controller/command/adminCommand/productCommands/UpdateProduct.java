package com.training.InternetStore.controller.command.adminCommand.productCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import com.training.InternetStore.model.entity.Product;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateProduct implements Command {
    private final Logger logger = LogManager.getLogger(UpdateProduct.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            Product product = userService.getProductById(Integer.parseInt(request.getParameter("productId")));
            product.setName(request.getParameter("name"));
            product.setCategory(userService.getCategoryByID(Integer.parseInt(request.getParameter("category"))));
            product.setPrice(Integer.parseInt(request.getParameter("price")));
            product.setSize(userService.getSizeByID(Integer.parseInt(request.getParameter("size"))));
            product.setColor(userService.getColorByID(Integer.parseInt(request.getParameter("color"))));
            product.setSex(Product.Sex.valueOf(request.getParameter("sex")));
            adminService.updateProduct(product);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return "redirect:/app/admin/productsManage";
        }

        return "redirect:/app/admin/productsManage";
    }
}
