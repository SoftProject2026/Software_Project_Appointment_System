package com.appointmentsystem.domain.models.enums;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public enum PropertyType {
	/** Apartment type property */
    APARTMENT("Apartment"),
    
    /** Villa type property */
    VILLA("Villa"),
    
    /** Commercial type property */
    COMMERCIAL("Commercial");
    
    /** The display name of the property type */
    private String displayName;
   
    
    /**
     * Constructor for PropertyType enum.
     * 
     * @param displayName the user-friendly name to display in the UI
     * 
     */
    PropertyType(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * Returns the display name of the property type.
     * 
     * @return the display name (e.g., "Apartment", "Villa", "Commercial")
     *
     */
    public String getDisplayName() {
        return displayName;
    }
}