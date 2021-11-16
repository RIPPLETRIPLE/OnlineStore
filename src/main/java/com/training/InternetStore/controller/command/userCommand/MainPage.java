package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        User user = request.getSession().getAttribute("user") == null ? null : (User) request.getSession().getAttribute("user");
        if (user == null || user.getRole() == User.Role.User) {
            List<Product> products;
            try {
                int amountOfProducts = adminService.getAmountOfProducts();
                String sortBy = request.getParameter("sortBy");
                String order = request.getParameter("order");

                int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
                int from = (page - 1) * 8 + 1;
                int to = amountOfProducts - from > 7 ? from + 7 : amountOfProducts;

                List<Integer> pages = new ArrayList<>();

                if (amountOfProducts > 8) {
                    for (int i = 0; i < (amountOfProducts % 8 != 0 ? (amountOfProducts / 8) + 1 : amountOfProducts / 8); i++) {
                        pages.add(i + 1);
                    }
                }

                request.setAttribute("pages", pages);
                products = adminService.getProductWithSortAndLimit(sortBy, order, from, to);
            } catch (Exception ex) {
                //logger
                String role = user == null ? "guest" : user.getRole().toString().toLowerCase(Locale.ROOT);
                return "redirect:/app/" + role + "/mainPage";
            }
            request.setAttribute("products", products);
            return JSPPageConstants.MAIN_PAGE;
        } else {
            return "/app/admin/productsManagePage";
        }
    }
}
