package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AddToCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        int productId;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            return "redirect:" + "/app/mainPage";
        }

        HttpSession session = request.getSession();

        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;

        if (user == null) {
            Product product;
            try {
                product = userService.getProductById(productId);
            } catch (FieldDontPresent fieldDontPresent) {
                return "redirect:" + "/app/mainPage";
            }
            CommandUtility.addProductToCartForUnloggedUser(session, product);
        }

        return "redirect:" + "/app/mainPage";
    }
}
