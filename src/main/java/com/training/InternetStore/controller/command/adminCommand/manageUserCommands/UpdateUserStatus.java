package com.training.InternetStore.controller.command.adminCommand.manageUserCommands;

import com.training.InternetStore.controller.command.Command;
import com.training.InternetStore.model.dao.exception.FieldDontPresent;
import com.training.InternetStore.model.dao.impl.JDBCCategoryDao;
import com.training.InternetStore.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UpdateUserStatus implements Command {
    private final Logger logger = LogManager.getLogger(UpdateUserStatus.class);
    @Override
    public String execute(HttpServletRequest request) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            User user = adminService.getUserByID(userId).orElseThrow(FieldDontPresent::new);

            user.setStatus(User.UserStatus.valueOf(request.getParameter("status")));
            adminService.updateUser(user);
        } catch (Exception ex) {
            logger.warn(ex.getMessage(), ex);
            return "redirect:/app/admin/usersManagePage";
        }

        return "redirect:/app/admin/usersManagePage";
    }
}
