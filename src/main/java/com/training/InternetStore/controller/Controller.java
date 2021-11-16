package com.training.InternetStore.controller;
import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.adminCommand.manageUserCommands.UpdateUserStatus;
import com.training.InternetStore.controller.command.adminCommand.manageUserCommands.UsersManagePage;
import com.training.InternetStore.controller.command.adminCommand.orderCommands.DeleteOrder;
import com.training.InternetStore.controller.command.adminCommand.orderCommands.OrdersManagePage;
import com.training.InternetStore.controller.command.adminCommand.orderCommands.UpdateOrderStatus;
import com.training.InternetStore.controller.command.adminCommand.productCommands.AddProduct;
import com.training.InternetStore.controller.command.adminCommand.productCommands.DeleteProduct;
import com.training.InternetStore.controller.command.adminCommand.productCommands.ProductsManagePage;
import com.training.InternetStore.controller.command.adminCommand.productCommands.UpdateProduct;
import com.training.InternetStore.controller.command.guestCommand.LogOut;
import com.training.InternetStore.controller.command.guestCommand.Login;
import com.training.InternetStore.controller.command.guestCommand.Registration;
import com.training.InternetStore.controller.command.userCommand.*;
import com.training.InternetStore.controller.command.userCommand.cartCommands.AddToCart;
import com.training.InternetStore.controller.command.userCommand.cartCommands.BuyFromCart;
import com.training.InternetStore.controller.command.userCommand.cartCommands.CartPage;
import com.training.InternetStore.controller.command.userCommand.cartCommands.ChangeProductQuantity;
import com.training.InternetStore.controller.command.userCommand.orderCommands.CancelRegisteredOrder;
import com.training.InternetStore.controller.command.userCommand.orderCommands.OrdersPage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Controller extends HttpServlet {
    private Map<String, Command> commands = new HashMap<>();

    public void init() {
        commands.put("registration", new Registration());
        commands.put("login", new Login());
        commands.put("mainPage", new MainPage());
        commands.put("logout", new LogOut());
        commands.put("addToCart", new AddToCart());
        commands.put("cartPage", new CartPage());
        commands.put("changeProductQuantity", new ChangeProductQuantity());
        commands.put("buyFromCart", new BuyFromCart());
        commands.put("ordersPage", new OrdersPage());
        commands.put("cancelRegisteredOrder", new CancelRegisteredOrder());
        commands.put("addProduct", new AddProduct());
        commands.put("productsManagePage", new ProductsManagePage());
        commands.put("deleteProduct", new DeleteProduct());
        commands.put("updateOrderStatus", new UpdateOrderStatus());
        commands.put("ordersManagePage", new OrdersManagePage());
        commands.put("deleteOrder", new DeleteOrder());
        commands.put("updateProduct", new UpdateProduct());
        commands.put("usersManagePage", new UsersManagePage());
        commands.put("updateUserStatus", new UpdateUserStatus());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        path = path.replaceAll(".*/app/.*/", "");

        Command command = commands.getOrDefault(path,
                (r) -> "mainPage");
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

}