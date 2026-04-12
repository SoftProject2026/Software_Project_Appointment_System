package com.appointmentsystem.domain.models;


/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Admin {
    private String id;
    private String username;
    private String password;

    /**
     * Constructs a new Admin with the specified credentials.
     * 
     * @param id the unique identifier for the admin
     * @param username the username for admin login
     * @param password the password for admin authentication
     * 
     *
     */
    public Admin(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
/**
 * 
 * @return the admin's ID
 */
    public String getId() { return id; }
    
    /**
     * 
     * @return the the admin's username
     */
    public String getUsername() { return username; }
    
    /**
     * 
     * @return the admin's password
     */
    public String getPassword() { return password; }

    
/**
 *
 *@return a formatted string containing admin information
 */
    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getUsername() + "'}";
    }
    
}
