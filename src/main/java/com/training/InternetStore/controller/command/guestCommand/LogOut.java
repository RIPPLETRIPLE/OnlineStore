package com.training.InternetStore.controller.command.guestCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.util.CommandUtility;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") != null) {
            CommandUtility.deleteUserFromLogged(request.getSession());
        }
        return "redirect:/app/guest/mainPage";
    }
}
