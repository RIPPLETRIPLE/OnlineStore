package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = session.getAttribute("user") == null ? null : (User) request.getSession().getAttribute("user");

        if (session.getAttribute("sortBy") == null || request.getParameter("sortBy") != null) {
            session.setAttribute("sortBy", request.getParameter("sortBy") == null ? "date" : request.getParameter("sortBy"));
        }
        System.out.println(session.getAttribute("sortBy"));
        if (session.getAttribute("order") == null || request.getParameter("order") != null) {
            session.setAttribute("order", request.getParameter("order") == null ? "asc" : request.getParameter("order"));
        }
        System.out.println(session.getAttribute("order"));
        if (session.getAttribute("filterParam") == null || request.getParameterValues("filterParam") != null) {
            session.setAttribute("filterParam", request.getParameterValues("filterParam"));
        }

        if (user == null || user.getRole() == User.Role.User) {
            List<Product> products;
            try {
                String[] filterParams = session.getAttribute("filterParam") == null ? new String[0]
                                                   : (String[]) session.getAttribute("filterParam");

                int amountOfProducts = adminService.getAmountOfProducts(filterParams);

                String sortBy = (String) session.getAttribute("sortBy");
                String order = (String) session.getAttribute("order");

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
                products = adminService.getProductWithSortAndLimit(sortBy, order, filterParams, from, to);
            } catch (Exception ex) {
                ex.printStackTrace();
                String role = user == null ? "guest" : user.getRole().toString().toLowerCase(Locale.ROOT);
                return "redirect:/app/" + role + "/mainPage";
            }
            request.setAttribute("colors", userService.getAllColors());
            request.setAttribute("categories", userService.getAllCategories());
            request.setAttribute("sizes", userService.getAllSizes());
            request.setAttribute("products", products);
            return JSPPageConstants.MAIN_PAGE;
        } else {
            return "/app/admin/productsManagePage";
        }
    }
}
