package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.persistence.PropertyRepository;

import java.util.List;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class PropertyService {
    
    private PropertyRepository propertyRepository;
    
    public PropertyService() {
        this.propertyRepository = new PropertyRepository();
    }
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    public void addProperty(Company c, Property p) {
    	p.setCompanyId(c.getId());
    	propertyRepository.save(p);
    	System.out.println("Property added!");
    }
    
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
    
    public void deleteProperty(String id) {
        propertyRepository.delete(id);
    }
    
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id);
    }
    
    public List<Property> getPropertiesByCompany(String companyId) {
        return propertyRepository.findByCompanyId(companyId);
    }
    
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }   

}