package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;


import com.appointmentsystem.persistence.CompanyRepository;
import com.appointmentsystem.persistence.PropertyRepository;


import java.time.LocalDateTime;
import java.util.List;


/**
 * Service class for managing company operations.
 * Handles company registration, login, approval, property management, and time slot scheduling.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class CompanyService {
    
    /** Repository for company data access operations */
	private CompanyRepository companyRepository;
	
	/** Repository for property data access operations */
    private PropertyRepository propertyRepository;

    /**
     * Default constructor that initializes repositories with new instances.
     */
    public CompanyService() {
        this.companyRepository = new CompanyRepository();
        this.propertyRepository = new PropertyRepository();
    }
    
    /**
     * Parameterized constructor for dependency injection (used in testing).
     * 
     * @param companyRepository the company repository
     * @param propertyRepository the property repository
     */
    public CompanyService(CompanyRepository companyRepository, PropertyRepository propertyRepository) {
        this.companyRepository = companyRepository;
        this.propertyRepository = propertyRepository;
    }

    /**
     * Registers a new company in the system.
     * 
     * @param c the company to register
     * @throws RuntimeException if username already exists or email is invalid
     */
	public void signup(Company c) {
        if (companyRepository.findByUsername(c.getUsername()) != null)
            throw new RuntimeException("Username exists");
        if(isValidEmail(c.getEmail()))
        	companyRepository.save(c);
        else throw new RuntimeException("Invalid Email");
    }

    /**
     * Authenticates a company user.
     * 
     * @param username the company username
     * @param password the company password
     * @return the authenticated Company object
     * @throws RuntimeException if credentials are invalid or company not verified
     */
    public Company login(String username, String password) {
        Company c = companyRepository.findByUsername(username);

        if (c == null || !c.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials");

        if (!c.isVerified())
            throw new RuntimeException("Company not approved");

        return c;
    }
	
    /**
     * Approves a company account by username.
     * 
     * @param username the username of the company to approve
     * @throws RuntimeException if company not found or already approved
     */
    public void approve(String username) {
        Company c = companyRepository.findByUsername(username);

        if (c == null)
            throw new RuntimeException("Not found");

        if (c.isVerified())
            throw new RuntimeException("Already approved");

        c.setVerified(true);
        companyRepository.update(c);
    }

    /**
     * Returns a formatted string of all registered companies.
     * 
     * @return string containing all companies or "No companies" if empty
     */
    public String printAllCompanys() {
    	List<Company> companies = companyRepository.findAll();
        if (companies.isEmpty()) {
            return "No companies";
        }
        StringBuilder sb = new StringBuilder();
        for (Company c : companies) {
            sb.append(c.toString()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * Approves a company by its company name.
     * 
     * @param name the company name to approve
     */
    public void approveCompany(String name) {
        Company c = companyRepository.findByCompanyName(name);
        if (c == null) {
            System.out.println("Company not found");
            return;
        }
        if (c.isVerified()) {
            System.out.println("Company already approved");
            return;
        }
        c.setVerified(true);
        companyRepository.update(c);
        
        System.out.println("Company approved successfully!");
    }

    public void addTimeSlotToProperty(Company c, int propertyIndex, String startInput) {
        List<Property> properties = propertyRepository.findByCompanyId(c.getId());

        if (properties.isEmpty()) {
            System.out.println("No properties found");
            return;
        }
        if (propertyIndex < 0 || propertyIndex >= properties.size()) {
            System.out.println("Invalid property index");
            return;
        }
        
        Property selected = properties.get(propertyIndex);

        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime startTime = LocalDateTime.parse(startInput, formatter);
            
            LocalDateTime now = LocalDateTime.now();
            if (startTime.isBefore(now)) {
                System.out.println("Cannot add time slot in the past. Please choose a future date and time.");
                return;
            }

            for (TimeSlot s : selected.getTimeSlots()) {
                if (s.getStartTime().equals(startTime)) {
                    System.out.println("This time slot already exists");
                    return;
                }
            }
            
            TimeSlot slot = new TimeSlot(startTime);
            selected.addTimeSlot(slot);
            propertyRepository.update(selected);
            System.out.println("Time slot added successfully!");

        } catch (Exception e) {
            System.out.println("Invalid date format. Use: yyyy-MM-dd HH:mm");
        }
    }
    
    /**
     * Validates email format.
     * 
     * @param email the email to validate
     * @return true if email format is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!email.contains(".")) {
            return false;
        }
        if (email.startsWith("@") || email.endsWith("@")) {
            return false;
        }
        return true;
    }
    
    
    

}
