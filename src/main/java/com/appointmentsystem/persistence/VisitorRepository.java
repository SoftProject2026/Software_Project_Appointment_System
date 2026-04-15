package com.appointmentsystem.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.appointmentsystem.domain.models.Visitor;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
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
    
   
    
    
    public void update(Visitor user) {
        if (user == null || !visitorStorage.containsKey(user.getId())) return;
        visitorStorage.put(user.getId(), user);
    }
    
    public void delete(String id) {
        if (id == null) return;
        visitorStorage.remove(id);
    }
    
    
    

}
