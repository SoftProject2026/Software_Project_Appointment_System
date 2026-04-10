package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Admin;

public class AdminService {
	
    private Admin admin = new Admin("1", "admin", "1234");

    public Admin login(String username, String password) {
        if (!admin.getUsername().equals(username) || !admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid admin login");
        }

        return admin;
    }

}