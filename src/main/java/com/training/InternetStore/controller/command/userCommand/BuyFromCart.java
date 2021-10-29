package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BuyFromCart implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            return "redirect:" + "/app/login";
        }
        
        return "redirect:" + "/app/cartPage";
    }
}
