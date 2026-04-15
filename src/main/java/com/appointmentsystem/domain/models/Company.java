package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Company{
	private String id;
    private String companyName;
    private String username;
    private String email;
    private String password;
    private String commercialRegister;
    private boolean isVerified;
    
    public Company(String id, String companyName, String username, String email, String password, String cr) {
		this.id = id;
		this.companyName = companyName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.commercialRegister = cr;
		this.isVerified = false;
    }
    
    public String getId() { return id; }
    
    public String getUsername(){ return username; }
    
    public String getPassword(){ return password; }

    public String getEmail(){ return email; }
    
    public String getCompanyName(){return companyName;}
    public void setCompanyName(String companyName) { 
        this.companyName = companyName; 
    }

    public String getCommercialRegister() { 
        return commercialRegister; 
    }
    //public void setCommercialRegister(String commercialRegister) { 
       // this.commercialRegister = commercialRegister; 
    //}


    public boolean isVerified() { 
        return isVerified; 
    }
    public void setVerified(boolean verified) { 
        isVerified = verified; 
    }

    @Override
    public String toString() {
    	return "Name: " + getCompanyName() + " | Verified: " + isVerified;
    }
}

	