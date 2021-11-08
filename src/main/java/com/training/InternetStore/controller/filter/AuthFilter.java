package com.training.InternetStore.controller.filter;

import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);
        HttpServletResponse httpResponse = ((HttpServletResponse) servletResponse);
        User user = httpRequest.getSession().getAttribute("user") == null ? null
                  : (User) httpRequest.getSession().getAttribute("user");

        if (user == null || user.getRole() != User.Role.Admin) {
            httpRequest.getRequestDispatcher(JSPPageConstants.AUTH_ERROR_PAGE).forward(httpRequest, httpResponse);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
