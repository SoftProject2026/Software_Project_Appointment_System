package com.appointmentsystem.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    
    /**
     * @return all visitors
     */
    public List<Visitor> findAll() {
        return new ArrayList<>(visitorStorage.values());
    }
    
    /**
     * @param user the visitor to update
     */
    public void update(Visitor user) {
        if (user == null || !visitorStorage.containsKey(user.getId())) return;
        visitorStorage.put(user.getId(), user);
    }
    
    /**
     * @param id the visitor ID to delete
     */
    public void delete(String id) {
        if (id == null) return;
        visitorStorage.remove(id);
    }
    
    /**
     * @param username the username
     * @return true if exists
     */
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return visitorStorage.values().stream()
                .anyMatch(u -> username.equals(u.getUsername()));
    }
    
    /**
     * @param email the email
     * @return true if exists
     */
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return visitorStorage.values().stream()
                .anyMatch(u -> email.equals(u.getEmail()));
    }
    
    /**
     * @return number of visitors
     */
    public int count() {
        return visitorStorage.size();
    }
    
    /** Clears all visitors (for testing). */
    public void clear() {
        visitorStorage.clear();
    }
}