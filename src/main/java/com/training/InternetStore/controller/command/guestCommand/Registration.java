package com.training.InternetStore.controller.command.guestCommand;

import com.mysql.cj.Session;
import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.controller.util.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        if (login == null || password == null) {
            return JSPPageConstants.REGISTRATION_PAGE;
        }

        if (!ValidationUtil.isLoginValid(login) || !ValidationUtil.isPasswordValid(password)) {
            request.setAttribute("error", "invalid data");
            return "redirect:/app/registration";
        }

        if (userService.createNewUser(login, password)) {
            return "redirect:/app/mainPage";
        }

        session.setAttribute("loginAlreadyExist", "true");
        return "redirect:/app/registration";
    }
}
