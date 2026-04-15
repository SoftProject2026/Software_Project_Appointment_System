package com.appointmentsystem.domain.models.enums;
/**
 * Represents the possible statuses of an appointment in the system.
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public enum AppointmentStatus {
	/** Appointment has been successfully confirmed and booked */
    CONFIRMED("Confirmed"),
    
    /** Appointment has been completed (took place) */
    COMPLETED("Completed"),
    
    /** Appointment has been cancelled */
    CANCELLED("Cancelled");
    
    /** User-friendly display name for the status */
    private String displayName;
    
    /**
     * Constructs an AppointmentStatus with a display name.
     * 
     * @param displayName the user-friendly name to display in the UI
     * 
     */
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    /**
     * Returns the user-friendly display name of the status.
     * 
     * @return the display name (e.g., "Confirmed", "Completed", "Cancelled")
     */
    public String getDisplayName() {
        return displayName;
    }
}