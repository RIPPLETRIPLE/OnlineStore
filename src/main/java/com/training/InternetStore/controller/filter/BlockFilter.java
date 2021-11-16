package com.training.InternetStore.controller.filter;

import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);
        HttpServletResponse httpResponse = ((HttpServletResponse) servletResponse);
        User user = httpRequest.getSession().getAttribute("user") == null ? null
                : ((User) httpRequest.getSession().getAttribute("user"));

        if (user != null) {
            if (user.getStatus().equals(User.UserStatus.Blocked) && !httpRequest.getRequestURI().contains("logout")) {
                httpRequest.getRequestDispatcher(JSPPageConstants.BLOCK_ERROR).forward(httpRequest, httpResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
