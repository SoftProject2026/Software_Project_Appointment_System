package com.appointmentsystem.domain.models.enums;

public enum AppointmentStatus {
    CONFIRMED("Confirmed"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");
    
    private String displayName;
    
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}