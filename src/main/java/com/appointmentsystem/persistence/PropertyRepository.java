package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.enums.PropertyStatus;
import com.appointmentsystem.domain.models.enums.PropertyType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyRepository {
    
    private Map<String, Property> propertyStorage = new HashMap<>();
    
    
    public void save(Property property) {
        if (property == null) return;
        propertyStorage.put(property.getId(), property);
    }
    
    public Property findById(String id) {
        if (id == null) return null;
        return propertyStorage.get(id);
    }
    
    public List<Property> findAll() {
        return new ArrayList<>(propertyStorage.values());
    }
    
    public List<Property> findByCompanyId(String companyId) {
        if (companyId == null) return new ArrayList<>();
        return propertyStorage.values().stream()
                .filter(p -> companyId.equals(p.getCompanyId()))
                .collect(Collectors.toList());
    }
    
    public List<Property> findByType(PropertyType type) {
        if (type == null) return new ArrayList<>();
        return propertyStorage.values().stream()
                .filter(p -> type == p.getType())
                .collect(Collectors.toList());
    }
    
    public List<Property> findByStatus(PropertyStatus status) {
        if (status == null) return new ArrayList<>();
        return propertyStorage.values().stream()
                .filter(p -> status == p.getStatus())
                .collect(Collectors.toList());
    }
    
    public List<Property> findByPriceRange(double min, double max) {
        return propertyStorage.values().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }
    
    public List<Property> findAvailable() {
        return propertyStorage.values().stream()
                .filter(Property::isAvailable)
                .collect(Collectors.toList());
    }
    
    public void update(Property property) {
        if (property == null || !propertyStorage.containsKey(property.getId())) return;
        propertyStorage.put(property.getId(), property);
    }
    
    public void delete(String id) {
        if (id == null) return;
        propertyStorage.remove(id);
    }
    
    public boolean exists(String id) {
        if (id == null) return false;
        return propertyStorage.containsKey(id);
    }
    
    public int count() {
        return propertyStorage.size();
    }
    
    public int countByCompanyId(String companyId) {
        if (companyId == null) return 0;
        return (int) propertyStorage.values().stream()
                .filter(p -> companyId.equals(p.getCompanyId()))
                .count();
    }
    
    public void clear() {
        propertyStorage.clear();
    }
}