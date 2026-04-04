package com.appointmentsystem.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Log {
    private List<LogEntry> entries;
    
    public static class LogEntry {
        private String id;
        private String userId;
        private String userRole;
        private String action;
        private String entityType;
        private String entityId;
        private String details;
        private LocalDateTime timestamp;
        
        public LogEntry(String userId, String userRole, String action, 
                        String entityType, String entityId, String details) {
            this.id = UUID.randomUUID().toString();
            this.userId = userId;
            this.userRole = userRole;
            this.action = action;
            this.entityType = entityType;
            this.entityId = entityId;
            this.details = details;
            this.timestamp = LocalDateTime.now();
        }
        
        public String getId() { return id; }
        public String getUserId() { return userId; }
        public String getUserRole() { return userRole; }
        public String getAction() { return action; }
        public String getEntityType() { return entityType; }
        public String getEntityId() { return entityId; }
        public String getDetails() { return details; }
        public LocalDateTime getTimestamp() { return timestamp; }
        
        public String getFormattedMessage() {
            return String.format("[%s] [%s] %s - %s: %s",
                timestamp, userRole, action, entityType, details);
        }
        
        @Override
        public String toString() {
            return getFormattedMessage();
        }
    }
    
    public Log() {
        this.entries = new ArrayList<>();
    }
    
    public void log(String userId, String userRole, String action, 
                    String entityType, String entityId, String details) {
        LogEntry entry = new LogEntry(userId, userRole, action, entityType, entityId, details);
        entries.add(entry);
    }
    
    public List<LogEntry> getAllEntries() {
        return new ArrayList<>(entries);
    }
    
    public List<LogEntry> getEntriesByUser(String userId) {
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (entry.getUserId().equals(userId)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    public List<LogEntry> getEntriesByAction(String action) {
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (entry.getAction().equals(action)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    public List<LogEntry> getEntriesByEntity(String entityType, String entityId) {
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry entry : entries) {
            if (entry.getEntityType().equals(entityType) && entry.getEntityId().equals(entityId)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    public void clearLogs() {
        entries.clear();
    }
    
    public int getLogsCount() {
        return entries.size();
    }
    
    public void printAllLogs() {
        for (LogEntry entry : entries) {
            System.out.println(entry.getFormattedMessage());
        }
    }
    
    @Override
    public String toString() {
        return "Log{entries=" + entries.size() + "}";
    }
}