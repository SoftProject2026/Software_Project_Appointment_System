package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.persistence.PropertyRepository;

import java.util.List;

/**
 * Service for property management.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class PropertyService {
    
    private PropertyRepository propertyRepository;
    
    public PropertyService() {
        this.propertyRepository = new PropertyRepository();
    }
    
    /**
     * @param propertyRepository the property repository
     */
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    /**
     * @param c the company
     * @param p the property to add
     */
    public void addProperty(Company c, Property p) {

    	p.setCompanyId(c.getId());
    	propertyRepository.save(p);
    	System.out.println("Property added!");

    }
    
    /**
     * @param c the company
     */
    public void viewMyProperties(Company c) {
        List<Property> properties = propertyRepository.findByCompanyId(c.getId());
        if (properties.isEmpty()) {
            System.out.println("\nNo properties");
            return;
        }
        for (int i = 0; i < properties.size(); i++) {
            System.out.println(i + ". " + properties.get(i));
        }
    }
    
    /**
     * @param id the property ID
     */
    public void deleteProperty(String id) {
        propertyRepository.delete(id);
    }
    
    /**
     * @param id the property ID
     * @return the property if found
     */
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id);
    }
    
    /**
     * @param companyId the company ID
     * @return properties of the company
     */
    public List<Property> getPropertiesByCompany(String companyId) {
        return propertyRepository.findByCompanyId(companyId);
    }
    
    /**
     * @return all properties
     */
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
}