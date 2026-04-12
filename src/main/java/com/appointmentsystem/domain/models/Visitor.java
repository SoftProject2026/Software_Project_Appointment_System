package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
/**
 *Represents a visitor (customer) in the appointment system. 
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Visitor {
    
    /** Unique identifier for the visitor */
    private String id;
    
    /** Full name of the visitor */
    private String name;
    
    /** Username for visitor login */
    private String username;
    
    /** Email address of the visitor */
    private String email;
    
    /** Password for visitor authentication */
    private String password;
    
    /** Phone number of the visitor */
    private String phone;
    
    /**
     * Constructs a new Visitor with all information.
     * 
     * @param id visitor ID
     * @param name visitor full name
     * @param username login username
     * @param email visitor email
     * @param password login password
     * @param phone visitor phone number
     */
    public Visitor(String id, String name, String username, String email, String password, String phone) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    
    /**
     * Default constructor for testing.
     */
    public Visitor() {
        // Default constructor
    }
    
    /**
     * @return the visitor ID
     */
    public String getId() { return id; }
    
    /**
     * @return the visitor name
     */
    public String getName() { return name; }
    
    /**
     * @return the visitor username
     */
    public String getUsername() { return username; }
    
    /**
     * @return the visitor password
     */
    public String getPassword() { return password; }
    
    /**
     * @return the visitor email
     */
    public String getEmail() { return email; }
    
    /**
     * @return the visitor name
     */
    public String getVisitorName() { return name; }
    
    /**
     * @param companyName the new visitor name
     */
    public void setVisitorName(String companyName) { 
        this.name = companyName; 
    }
    
    /**
     * @return the visitor phone number
     */
    public String getPhone() { return phone; }
    
    /**
     * @param phone the new phone number
     */
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
    
    @Override
    public String toString() {
        return "Visitor{id='" + getId() + "', name='" + getVisitorName() + "', phone='" + phone + "'}";
    }
}