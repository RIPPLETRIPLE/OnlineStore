package com.training.InternetStore.controller.command;

import com.training.InternetStore.model.service.impl.AdminService;
import com.training.InternetStore.model.service.impl.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Command {
    UserService userService = UserService.getInstance();
    String execute(HttpServletRequest request) throws ServletException, IOException;
}
