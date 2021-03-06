package com.training.InternetStore.controller.filter;

import com.training.InternetStore.controller.constants.JSPPageConstants;
import com.training.InternetStore.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);
        HttpServletResponse httpResponse = ((HttpServletResponse) servletResponse);
        User user = httpRequest.getSession().getAttribute("user") == null ? null
                : ((User) httpRequest.getSession().getAttribute("user"));

        String userRole = user == null ? "guest" : user.getRole().toString().toLowerCase(Locale.ROOT);

        String roleAccess = "guest";


        Matcher m = Pattern.compile("(?<=\\/app\\/).*?(?=\\/)").matcher(httpRequest.getRequestURI());

        if (m.find()) {
            roleAccess = m.group();
        }

        if (!roleAccess.equals(userRole) && !httpRequest.getRequestURI().equals("")) {
            httpRequest.getRequestDispatcher(JSPPageConstants.AUTH_ERROR_PAGE).forward(httpRequest, httpResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
