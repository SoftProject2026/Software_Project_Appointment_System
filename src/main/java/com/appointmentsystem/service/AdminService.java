package com.appointmentsystem.service;

import java.util.List;

import com.appointmentsystem.domain.models.Admin;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.persistence.CompanyRepository;

public class AdminService {
	
    private Admin admin = new Admin("1", "admin", "1234");
    private CompanyRepository companyRepository = new CompanyRepository();

    public Admin login(String username, String password) {
        if (!admin.getUsername().equals(username) || !admin.getPassword().equals(password)) {
            throw new RuntimeException("Invalid admin login");
        }

        return admin;
    }

}