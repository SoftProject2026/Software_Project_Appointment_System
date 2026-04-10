package com.appointmentsystem.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.appointmentsystem.domain.models.Visitor;

public class VisitorRepository {

    private static Map<String, Visitor> visitorStorage = new HashMap<>();
    
    
    public void save(Visitor user) {
        if (user == null) return;
        visitorStorage.put(user.getId(), user);
    }
    
    public Visitor findById(String id) {
        if (id == null) return null;
        return visitorStorage.get(id);
    }
    
    public Visitor findByUsername(String username) {
        if (username == null) return null;
        return visitorStorage.values().stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Visitor> findAll() {
        return new ArrayList<>(visitorStorage.values());
    }
    
    
    public void update(Visitor user) {
        if (user == null || !visitorStorage.containsKey(user.getId())) return;
        visitorStorage.put(user.getId(), user);
    }
    
    public void delete(String id) {
        if (id == null) return;
        visitorStorage.remove(id);
    }
    
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return visitorStorage.values().stream()
                .anyMatch(u -> username.equals(u.getUsername()));
    }
    
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return visitorStorage.values().stream()
                .anyMatch(u -> email.equals(u.getEmail()));
    }
    
    public int count() {
        return visitorStorage.size();
    }
    
    public void clear() {
    	visitorStorage.clear();
    }
}
