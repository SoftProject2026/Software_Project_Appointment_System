package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Log.LogEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogRepository {
    
    private List<LogEntry> entries = new ArrayList<>();
    
    
    public void save(LogEntry entry) {
        if (entry == null) return;
        entries.add(entry);
    }
    
    public void saveAll(List<LogEntry> newEntries) {
        if (newEntries == null) return;
        entries.addAll(newEntries);
    }
    
    public void delete(LogEntry entry) {
        if (entry == null) return;
        entries.remove(entry);
    }
    
    public void deleteById(String id) {
        if (id == null) return;
        entries.removeIf(e -> id.equals(e.getId()));
    }
    
    public List<LogEntry> findAll() {
        return new ArrayList<>(entries);
    }
    
    // Query methods
    public List<LogEntry> findByUserId(String userId) {
        if (userId == null) return new ArrayList<>();
        return entries.stream()
                .filter(e -> userId.equals(e.getUserId()))
                .collect(Collectors.toList());
    }
    
    public List<LogEntry> findByUserRole(String userRole) {
        if (userRole == null) return new ArrayList<>();
        return entries.stream()
                .filter(e -> userRole.equals(e.getUserRole()))
                .collect(Collectors.toList());
    }
    
    public List<LogEntry> findByAction(String action) {
        if (action == null) return new ArrayList<>();
        return entries.stream()
                .filter(e -> action.equals(e.getAction()))
                .collect(Collectors.toList());
    }
    
    public List<LogEntry> findByEntity(String entityType, String entityId) {
        if (entityType == null || entityId == null) return new ArrayList<>();
        return entries.stream()
                .filter(e -> entityType.equals(e.getEntityType()) && entityId.equals(e.getEntityId()))
                .collect(Collectors.toList());
    }
    
    public void clear() {
        entries.clear();
    }
    
    public int count() {
        return entries.size();
    }
}