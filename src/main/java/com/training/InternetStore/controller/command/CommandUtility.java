package com.training.InternetStore.controller.command;


import com.training.InternetStore.model.entity.Order;
import com.training.InternetStore.model.entity.Product;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.entity.enums.OrderStatus;
import com.training.InternetStore.model.service.impl.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CommandUtility {
    public static boolean checkUserIsLogged(HttpSession session, User user) {
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        if (loggedUsers.stream().anyMatch(userLogin::equals)) {
            return true;
        }
        loggedUsers.add(userLogin);
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return false;
    }

    public static void deleteUserFromLogged(HttpSession session) {
        User user = (User) session.getAttribute("user");
        String userLogin = user.getLogin();
        HashSet<String> loggedUsers = (HashSet<String>) session.getServletContext().getAttribute("loggedUsers");
        if (loggedUsers == null) {
            loggedUsers = new HashSet<>();
        }
        loggedUsers.removeIf(uL -> uL.equals(userLogin));
        session.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        session.removeAttribute("user");
    }

    public static void addProductToCartForUnloggedUser(HttpSession session, Product product, int quantity) {
        List<Order> cart = getCart(session);

        if (cart.stream().anyMatch((c) -> c.getProduct().equals(product))) {
            cart.forEach(order -> {
                if (order.getQuantity() + quantity > 0 && order.getProduct().equals(product)) {
                    order.setQuantity(order.getQuantity() + quantity);
                }
            });
        } else {
            cart.add(Order.createOrder(null, product, quantity, OrderStatus.Unregistered));
        }

        session.setAttribute("cart", cart);
    }

    public static void removeProductFromCardForUnloggedUser(HttpSession session, Product product) {
        List<Order> cart = getCart(session);

        cart = cart.stream().filter(c -> !c.getProduct().equals(product)).collect(Collectors.toList());

        session.setAttribute("cart", cart);
    }

    private static List<Order> getCart(HttpSession session) {
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new ArrayList<Order>());
        }

        return (List<Order>) session.getAttribute("cart");
    }


    public static Optional<Product> extractProductFromForm(HttpServletRequest request, UserService userService) {
        final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
        final int MAX_REQUEST_SIZE = 1024 * 1024;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MAX_MEMORY_SIZE);

        String uploadFolder = request.getServletContext().getRealPath("").replaceAll("OnlineStore.*", "") + "OnlineStore\\src\\main\\webapp\\product-image";

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_REQUEST_SIZE);

        Product product = null;

        try {
            List<FileItem> items = upload.parseRequest(request);

            int categoryId = Integer.parseInt(items.get(0).getString());
            int colorId = Integer.parseInt(items.get(1).getString());
            int sizeId = Integer.parseInt(items.get(2).getString());
            Product.Sex sex = Product.Sex.valueOf(items.get(3).getString());
            int price = Integer.parseInt(items.get(4).getString());
            String name = items.get(5).getString();
            String image = items.get(6).getName();

            Product.Category category = userService.getCategoryByID(categoryId);
            Product.Color color = userService.getColorByID(colorId);
            Product.Size size = userService.getSizeByID(sizeId);

            product = Product.createProduct(0, name, price, sex, image, category, color, size);

            loadFilesToDirectory(uploadFolder, items);
        } catch (Exception ex) {
            return Optional.empty();
        }
        return Optional.of(product);
    }

    private static void loadFilesToDirectory(String uploadFolder, List<FileItem> items) throws Exception {
        for (FileItem item : items) {
            if (!item.isFormField()) {
                String fileName = new File(item.getName()).getName();
                String filePath = uploadFolder + File.separator + fileName;
                File uploadedFile = new File(filePath);
                item.write(uploadedFile);
            }
        }
    }

}
