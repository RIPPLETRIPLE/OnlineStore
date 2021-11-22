package com.training.InternetStore.controller.filter;

import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.entity.User;
import com.training.InternetStore.model.service.impl.AdminService;
import com.training.InternetStore.model.service.impl.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateUserFilter implements Filter {
    private final Logger logger = LogManager.getLogger(UpdateUserFilter.class);
    private final AdminService adminService = AdminService.getInstance();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);

        User user = httpRequest.getSession().getAttribute("user") == null ? null
                : ((User) httpRequest.getSession().getAttribute("user"));

        if (user != null) {
            httpRequest.getSession().removeAttribute("user");
            try {
                httpRequest.getSession().setAttribute("user", adminService.getUserByID((int) user.getId()).orElseThrow(FieldDontPresent::new));
            } catch (FieldDontPresent e) {
                logger.error(e.getMessage(), e);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
