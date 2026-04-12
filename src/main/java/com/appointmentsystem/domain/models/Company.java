package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
/**
 *  Represents a company that registers in the appointment system.
 *  
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Company{
	 /** Unique identifier for the company */
    private String id;
    
    /** Official name of the company */
    private String companyName;
    
    /** Username used for company login */
    private String username;
    
    /** Email address of the company for communication */
    private String email;
    
    /** Password for company authentication */
    private String password;
    
    /** Commercial register number for legal verification */
    private String commercialRegister;
    
    /** Indicates whether the company has been approved by admin */
    private boolean isVerified;
    
    /**
     * Constructs a new Company with the specified information.
     * Newly created companies are initially unverified (isVerified = false).
     * 
     * @param id the unique identifier for the company
     * @param companyName the official name of the company
     * @param username the username for company login
     * @param email the email address of the company
     * @param password the password for company authentication
     * @param cr the commercial register number
     * 
     * 
     */
    public Company(String id, String companyName, String username, String email, String password, String cr) {
		this.id = id;
		this.companyName = companyName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.commercialRegister = cr;
		this.isVerified = false;
    }
    
    /**
     * Returns the unique identifier of the company.
     * 
     * @return the company ID
     */
    public String getId() { return id; }
    
    /**
     * Returns the username of the company.
     * 
     * @return the company username
     */
    public String getUsername(){ return username; }
    
    /**
     * Returns the password of the company.
     * 
     * @return the company password
     */
    public String getPassword(){ return password; }

    /**
     * Returns the email address of the company.
     * 
     * @return the company email
     */
    public String getEmail(){ return email; }
    
    /**
     * Returns the official name of the company.
     * 
     * @return the company name
     */
    public String getCompanyName(){return companyName;}
    
    /**
     * Sets the official name of the company.
     * 
     * @param companyName the new company name
     */
    public void setCompanyName(String companyName) { 
        this.companyName = companyName; 
    }

    /**
     * Returns the commercial register number of the company.
     * 
     * @return the commercial register number
     */
    public String getCommercialRegister() { 
        return commercialRegister; 
    }
    
    /**
     * Sets the commercial register number of the company.
     * 
     * @param commercialRegister the new commercial register number
     */
    public void setCommercialRegister(String commercialRegister) { 
        this.commercialRegister = commercialRegister; 
    }


    /**
     * Checks if the company has been verified by the admin.
     * 
     * @return true if the company is verified, false otherwise
     */
    public boolean isVerified() { 
        return isVerified; 
    }
    
    /**
     * Sets the verification status of the company.
     * 
     * @param verified true to mark as verified, false to mark as unverified
     */
    public void setVerified(boolean verified) { 
        isVerified = verified; 
    }

    /**
     * Returns a string representation of the company.
     * The format includes the company name and verification status.
     * 
     * @return a formatted string containing company information
     * 
     */
    @Override
    public String toString() {
    	return "Name: " + getCompanyName() + " | Verified: " + isVerified;
    }
}

	