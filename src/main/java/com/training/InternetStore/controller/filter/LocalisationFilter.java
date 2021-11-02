package com.training.InternetStore.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LocalisationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String lang = servletRequest.getParameter("lang");
        if (lang != null) {
            req.getSession().setAttribute("lang", lang);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
