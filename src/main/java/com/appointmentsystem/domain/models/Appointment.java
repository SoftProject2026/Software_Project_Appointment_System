package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private String id;
    private String propertyId;
    private String visitorId;
    private String companyId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AppointmentStatus status;
    private LocalDateTime createdAt;
    private String notes;
    private boolean visitorAttended;
    
    public Appointment(String propertyId, String visitorId, String companyId, 
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID().toString();
        this.propertyId = propertyId;
        this.visitorId = visitorId;
        this.companyId = companyId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = AppointmentStatus.SCHEDULED;
        this.createdAt = LocalDateTime.now();
        this.visitorAttended = false;
    }
    
    public Appointment(String id, String propertyId, String visitorId, String companyId,
                       LocalDateTime startTime, LocalDateTime endTime, AppointmentStatus status) {
        this.id = id;
        this.propertyId = propertyId;
        this.visitorId = visitorId;
        this.companyId = companyId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.visitorAttended = false;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    
    public String getVisitorId() { return visitorId; }
    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }
    
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public boolean didVisitorAttend() { return visitorAttended; }
    public void setVisitorAttended(boolean visitorAttended) { this.visitorAttended = visitorAttended; }
    
    public boolean isToday() {
        return startTime.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
    
    public boolean isInFuture() {
        return startTime.isAfter(LocalDateTime.now());
    }
    
    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
    
    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }
    
    @Override
    public String toString() {
        return "Appointment{id='" + id + "', propertyId='" + propertyId + "', visitorId='" + visitorId + "', status=" + status + "}";
    }
}