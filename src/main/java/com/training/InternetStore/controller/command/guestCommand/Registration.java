package com.training.InternetStore.controller.command.guestCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.controller.util.ValidationUtil;
import com.training.InternetStore.model.entity.User;

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

        String registrationPage = "redirect:/app/guest/registration";

        if (!ValidationUtil.isLoginValid(login) || !ValidationUtil.isPasswordValid(password)) {
            request.setAttribute("error", "invalid data");
            return registrationPage;
        }

        if (userService.createNewUser(User.createUser(login, password, User.Role.User, User.UserStatus.Unblocked))) {
            return "redirect:/app/guest/login";
        }

        session.setAttribute("loginAlreadyExist", "true");
        return registrationPage;
    }
}
