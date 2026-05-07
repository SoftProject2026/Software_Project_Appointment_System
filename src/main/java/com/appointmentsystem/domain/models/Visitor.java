package com.appointmentsystem.domain.models;


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
    

    @Override
    public String toString() {

        return "Visitor{ name='" + getVisitorName() + "', phone='" + phone + "}";

    }
}
