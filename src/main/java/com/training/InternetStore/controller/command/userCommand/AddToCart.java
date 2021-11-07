package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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


        Product product;
        try {
            product = userService.getProductById(productId);
        } catch (FieldDontPresent fieldDontPresent) {
            return "redirect:" + "/app/mainPage";
        }
        if (user == null) {
            CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
        } else {
            Order order = Order.createOrder(user, product, 1, OrderStatus.valueOf("Unregistered"));
            if (!userService.addOrder(order)) {
                List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
                try {
                    order = orders.stream().filter((e) -> e.getProduct().getId() == productId).findFirst().orElseThrow(FieldDontPresent::new);
                } catch (FieldDontPresent e) {
                    return "redirect:" + "/app/mainPage";
                }

                userService.incrementOrderQuantity(order);
            }
        }

        return "redirect:" + "/app/mainPage";
    }
}
