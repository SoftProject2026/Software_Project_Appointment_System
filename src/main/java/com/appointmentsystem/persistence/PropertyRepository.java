package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Property;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyRepository {
    
	private static Map<String, Property> propertyStorage = new HashMap<>();
    
    
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

}