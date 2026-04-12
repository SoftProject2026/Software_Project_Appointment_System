package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository for Property CRUD operations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class PropertyRepository {
    
    private static Map<String, Property> propertyStorage = new HashMap<>();
    
    /**
     * @param property the property to save
     */
    public void save(Property property) {
        if (property == null) return;
        propertyStorage.put(property.getId(), property);
    }
    
    /**
     * @param id the property ID
     * @return the property if found
     */
    public Property findById(String id) {
        if (id == null) return null;
        return propertyStorage.get(id);
    }
    
    /**
     * @return all properties
     */
    public List<Property> findAll() {
        return new ArrayList<>(propertyStorage.values());
    }
    
    /**
     * @param companyId the company ID
     * @return properties owned by the company
     */
    public List<Property> findByCompanyId(String companyId) {
        if (companyId == null) return new ArrayList<>();
        return propertyStorage.values().stream()
                .filter(p -> companyId.equals(p.getCompanyId()))
                .collect(Collectors.toList());
    }
    
    /**
     * @param property the property to update
     */
    public void update(Property property) {
        if (property == null || !propertyStorage.containsKey(property.getId())) return;
        propertyStorage.put(property.getId(), property);
    }
    
    /**
     * @param id the property ID to delete
     */
    public void delete(String id) {
        if (id == null) return;
        propertyStorage.remove(id);
    }
    
    /**
     * @param id the property ID
     * @return true if exists
     */
    public boolean exists(String id) {
        if (id == null) return false;
        return propertyStorage.containsKey(id);
    }
}