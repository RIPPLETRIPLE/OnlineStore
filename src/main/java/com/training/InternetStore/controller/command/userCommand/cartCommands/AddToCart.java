package com.training.InternetStore.controller.command.userCommand.cartCommands;

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
import java.util.Locale;

public class AddToCart implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        int productId;
        HttpSession session = request.getSession();

        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        User.Role role = user != null ? user.getRole() : User.Role.Guest;
        String mainPage = "redirect:" + "/app/" + role.toString().toLowerCase(Locale.ROOT) + "/mainPage";

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            return mainPage;
        }

        Product product;
        try {
            product = userService.getProductById(productId);
        } catch (FieldDontPresent fieldDontPresent) {
            return mainPage;
        }

        if (user == null) {
            CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
        } else {
            Order order = Order.createOrder(user, product, 1, OrderStatus.valueOf("Unregistered"));
            if (!userService.createOrder(order)) {
                List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
                try {
                    order = orders.stream().filter((e) -> e.getProduct().getId() == productId).findFirst().orElseThrow(FieldDontPresent::new);
                } catch (FieldDontPresent e) {
                    return mainPage;
                }

                userService.incrementOrderQuantity(order);
            }
        }

        return mainPage;

    }
}
