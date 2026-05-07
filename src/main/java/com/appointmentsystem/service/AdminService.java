package com.appointmentsystem.service;



import com.appointmentsystem.domain.models.Admin;


/**
 * Service for admin operations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class AdminService {
    
    private Admin admin = new Admin("1", "admin", "1234");

    /**
     * Authenticates admin with username and password.
     * 
     * @param username the admin's username
     * @param password the admin's password
     * @return the authenticated Admin
     * @throws RuntimeException if credentials are invalid
     */
    public Admin login(String username, String password) {
        if (!admin.getUsername().equals(username) || !admin.getPassword().equals(password)) {
        	throw new IllegalArgumentException("Invalid admin login");
        }

        return admin;
    }
}
