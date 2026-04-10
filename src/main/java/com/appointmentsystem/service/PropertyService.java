package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.persistence.PropertyRepository;

import java.util.List;

public class PropertyService {
    
    private PropertyRepository propertyRepository;
    
    public PropertyService() {
        this.propertyRepository = new PropertyRepository();
    }
    
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }
    
    
    public void createProperty(Property property) {
        if (property == null) return;
        propertyRepository.save(property);
    }
    
    public Property getPropertyById(String id) {
        return propertyRepository.findById(id);
    }
    
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    
    
    public void deleteProperty(String id) {
        propertyRepository.delete(id);
    }
    
    
    public List<Property> getPropertiesByCompany(String companyId) {
        return propertyRepository.findByCompanyId(companyId);
    }

}