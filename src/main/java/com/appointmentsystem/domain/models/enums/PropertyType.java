package com.appointmentsystem.domain.models.enums;

public enum PropertyType {
    APARTMENT("Apartment"),
    VILLA("Villa"),
    COMMERCIAL("Commercial");
    
    private String displayName;
    
    PropertyType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}