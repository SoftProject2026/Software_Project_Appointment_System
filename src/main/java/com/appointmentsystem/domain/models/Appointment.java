package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Appointment {
    private String id;
    private String propertyId;
    private String visitorId;
    private AppointmentStatus status;
    private AppointmentType type;
    private TimeSlot slot;
    
    public Appointment(String propertyId, String visitorId, TimeSlot slot, AppointmentType type) {
        this.id = UUID.randomUUID().toString();
        //this.id = id;
        this.propertyId = propertyId;
        this.visitorId = visitorId;
        this.slot = slot;
        this.type = type;
        this.status = AppointmentStatus.CONFIRMED;
        //this.slot.setAvailable(false);
    }
    
    public TimeSlot getSlot() { return slot; }
    public void setSlot(TimeSlot slot) {this.slot = slot;}
    
    public String getId() { return id; }
    
    public String getPropertyId() { return propertyId; }
    public void setPropertyId(String propertyId) { this.propertyId = propertyId; }
    
    public String getVisitorId() { return visitorId; }
    public void setVisitorId(String visitorId) { this.visitorId = visitorId; }

    public AppointmentType getType() { return type; }
    
    public AppointmentStatus getStatus() { return status; }
    public void setStatus(AppointmentStatus status) { this.status = status; }
    
    
    public void confirm() {
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
    
    public void complete() {
        this.status = AppointmentStatus.COMPLETED;
    }
    
    public boolean isFuture() {
        return slot.getStartTime().isAfter(java.time.LocalDateTime.now());
    }
    
    @Override
    public String toString() {
        return "Appointment{id='" + id + "', propertyId='" + propertyId + "', visitorId='" + visitorId + "', status=" + status + "}";
    }
}