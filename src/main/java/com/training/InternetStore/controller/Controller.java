package com.training.InternetStore.controller;
import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.guestCommand.LogOut;
import com.training.InternetStore.controller.command.guestCommand.Login;
import com.training.InternetStore.controller.command.guestCommand.Registration;
import com.training.InternetStore.controller.command.userCommand.AddToCart;
import com.training.InternetStore.controller.command.userCommand.CartPage;
import com.training.InternetStore.controller.command.userCommand.MainPage;

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

        path = path.replaceAll(".*/app/", "");

        Command command = commands.getOrDefault(path,
                (r) -> "logout");
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }

    }

}