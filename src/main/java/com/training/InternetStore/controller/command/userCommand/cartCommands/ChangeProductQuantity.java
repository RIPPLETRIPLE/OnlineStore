package com.training.InternetStore.controller.command.userCommand.cartCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ChangeProductQuantity implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        int productId;
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") != null ? (User) session.getAttribute("user") : null;
        User.Role role = user != null ? user.getRole() : User.Role.Guest;
        String cartPage = "redirect:" + "/app/" + role.toString().toLowerCase(Locale.ROOT) + "/cartPage";
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            return cartPage;
        }
        Product product;

        try {
            product = userService.getProductById(productId);
        } catch (FieldDontPresent fieldDontPresent) {
            return cartPage;
        }

        if (user == null) {
            if (action.equals("increment")) {
                CommandUtility.addProductToCartForUnloggedUser(session, product, 1);
            }
            if (action.equals("decrement")) {
                CommandUtility.addProductToCartForUnloggedUser(session, product, -1);
            }
            if (action.equals("remove")) {
                CommandUtility.removeProductFromCardForUnloggedUser(session, product);
            }
        } else {
            List<Order> orders = userService.getOrdersByStatus(user, OrderStatus.Unregistered);
            Order order;
            try {
                order = orders.stream().filter((e) -> e.getProduct().getId() == productId).findFirst().orElseThrow(FieldDontPresent::new);
            } catch (FieldDontPresent e) {
                return cartPage;
            }

            if (action.equals("increment")) {
                userService.incrementOrderQuantity(order);
            }
            if (action.equals("decrement")) {
                userService.decrementOrderQuantity(order);
            }
            if (action.equals("remove")) {
                userService.deleteOrder(order);
            }
        }
        return cartPage;
    }
}
