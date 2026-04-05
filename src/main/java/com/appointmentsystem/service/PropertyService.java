package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.enums.PropertyStatus;
import com.appointmentsystem.domain.models.enums.PropertyType;
import com.appointmentsystem.persistence.PropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    
    public void updateProperty(Property property) {
        propertyRepository.update(property);
    }
    
    public void deleteProperty(String id) {
        propertyRepository.delete(id);
    }
    
    // ========== Query Methods ==========
    
    public List<Property> getPropertiesByCompany(String companyId) {
        return propertyRepository.findByCompanyId(companyId);
    }
    
    public List<Property> getPropertiesByType(PropertyType type) {
        return propertyRepository.findByType(type);
    }
    
    public List<Property> getPropertiesByStatus(PropertyStatus status) {
        return propertyRepository.findByStatus(status);
    }
    
    public List<Property> getAvailableProperties() {
        return propertyRepository.findAvailable();
    }
    
    public List<Property> getPropertiesByPriceRange(double min, double max) {
        return propertyRepository.findByPriceRange(min, max);
    }
    
    public List<Property> searchProperties(String keyword) {
        if (keyword == null) return new ArrayList<>();
        return getAllProperties().stream()
                .filter(p -> p.getTitle() != null && p.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                           p.getDescription() != null && p.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    
    
    public void markAsSold(String propertyId) {
        Property property = propertyRepository.findById(propertyId);
        if (property != null) {
            property.markAsSold();
            propertyRepository.update(property);
        }
    }
    
    public void incrementViewCount(String propertyId) {
        Property property = propertyRepository.findById(propertyId);
        if (property != null) {
            property.incrementViewCount();
            propertyRepository.update(property);
        }
    }
    
    public void updateStatus(String propertyId, PropertyStatus status) {
        Property property = propertyRepository.findById(propertyId);
        if (property != null) {
            property.setStatus(status);
            propertyRepository.update(property);
        }
    }
    
    public void updateAvailability(String propertyId, boolean available) {
        Property property = propertyRepository.findById(propertyId);
        if (property != null) {
            property.setAvailable(available);
            propertyRepository.update(property);
        }
    }
    
    // ========== Statistics ==========
    
    public int getTotalPropertiesCount() {
        return propertyRepository.count();
    }
    
    public int getPropertiesCountByCompany(String companyId) {
        return propertyRepository.countByCompanyId(companyId);
    }
    
    public int getAvailablePropertiesCount() {
        return getAvailableProperties().size();
    }
    
    public int getPropertiesCountByType(PropertyType type) {
        return getPropertiesByType(type).size();
    }
    
    public double getAveragePrice() {
        return getAllProperties().stream()
                .mapToDouble(Property::getPrice)
                .average()
                .orElse(0.0);
    }
}