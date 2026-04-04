package com.appointmentsystem.domain.models.enums;

public enum PropertyStatus {
    AVAILABLE("Available"),
    BOOKED("Booked"),
    SOLD("Sold"),
    UNDER_MAINTENANCE("Under Maintenance");
    
    private String displayName;
    
    PropertyStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}