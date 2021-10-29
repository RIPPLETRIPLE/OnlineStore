package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeProductQuantity implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int productId;
        String action = request.getParameter("action");

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            return "redirect:" + "/app/cartPage";
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
            if (action.equals("increment")) {
                CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
            }
            if (action.equals("decrement")) {
                CommandUtility.addProductToCartForUnloggedUser(session, product, -1);
            }
            if (action.equals("remove")) {
                CommandUtility.removeProductFromCardForUnloggedUser(session, product);
            }
        }
        return "redirect:" + "/app/cartPage";
    }
}
