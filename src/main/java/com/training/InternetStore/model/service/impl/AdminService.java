package com.training.InternetStore.model.service.impl;

public class AdminService {
    private static AdminService adminService;

    public static AdminService getInstance() {
        if (adminService == null) {
            synchronized (UserService.class) {
                adminService = new AdminService();
            }
        }
        return adminService;
    }

    private AdminService() {
    }

}
