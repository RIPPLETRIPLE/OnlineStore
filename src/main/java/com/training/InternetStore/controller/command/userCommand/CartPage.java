package com.training.InternetStore.controller.command.userCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CartPage implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        return JSPPageConstants.CART_PAGE;
    }
}
