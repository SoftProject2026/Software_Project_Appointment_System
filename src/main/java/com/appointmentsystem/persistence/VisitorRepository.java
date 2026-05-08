package com.appointmentsystem.persistence;

import java.util.HashMap;
import java.util.Map;


import com.appointmentsystem.domain.models.Visitor;

/**
 * Repository for Visitor CRUD operations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class VisitorRepository {

    private static Map<String, Visitor> visitorStorage = new HashMap<>();
    
    /**
     * @param user the visitor to save
     */
    public void save(Visitor user) {
        if (user == null) return;
        visitorStorage.put(user.getId(), user);
    }
    
    /**
     * @param id the visitor ID
     * @return the visitor if found
     */
    public Visitor findById(String id) {
        if (id == null) return null;
        return visitorStorage.get(id);
    }
    
    /**
     * @param username the username
     * @return the visitor if found
     */
    public Visitor findByUsername(String username) {
        if (username == null) return null;
        return visitorStorage.values().stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);
    }
    


}

