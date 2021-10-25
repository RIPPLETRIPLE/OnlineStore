package com.training.InternetStore.controller.command.guestCommand;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.controller.command.CommandUtility;
import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login implements Command {
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            return JSPPageConstants.LOGIN_PAGE;
        }

        System.out.println("logging");
        if (userService.DBContainsUser(login, password)) {
            User user = User.createUser(login, password);
            if (CommandUtility.checkUserIsLogged(request.getSession(), user)) {
                session.setAttribute("error", true);
                session.setAttribute("userAlreadyLogged", true);
                return "redirect:" + "/app/login";
            }

            session.setAttribute("user", user);
            return "redirect:" + "/app/mainPage";
        }

        session.setAttribute("error", true);
        session.setAttribute("wrongData", true);
        return "redirect:" + "/app/login";
    }
}
