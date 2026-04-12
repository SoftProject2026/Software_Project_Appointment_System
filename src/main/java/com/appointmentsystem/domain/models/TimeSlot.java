package com.appointmentsystem.domain.models;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * Represents a time slot for appointments in the system.
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */

public final class TimeSlot {
    
    /** Formatter for date and time display */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    /** The start time of the time slot */
    private LocalDateTime startTime;
    
    /** Indicates whether this time slot is available for booking */
    private boolean isAvailable = true;
    
    /**
     * Constructs a new TimeSlot with the specified start time.
     * 
     * @param startTime the start time of the time slot (must be in the future)
     * @throws IllegalArgumentException if the start time is in the past
     */
    public TimeSlot(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create a time slot in the past");
        }
        this.startTime = startTime;
        this.isAvailable = true;
    }
    
    /**
     * Returns the start time of the time slot.
     * 
     * @return the start time
     */
    public LocalDateTime getStartTime() { 
        return startTime; 
    }
    
    /**
     * Checks if this time slot is available for booking.
     * 
     * @return true if available, false if booked
     */
    public boolean isAvailable() {
        return isAvailable;
    }
    
    /**
     * Sets the availability status of this time slot.
     * 
     * @param available true to mark as available, false to mark as booked
     */
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    /**
     * Calculates the end time based on appointment type duration.
     * 
     * @param durationMinutes the duration of the appointment in minutes
     * @return the calculated end time
     */
    public LocalDateTime calculateEndTime(int durationMinutes) {
        return startTime.plusMinutes(durationMinutes);
    }
    
    /**
     * Checks if this time slot is scheduled for a future date and time.
     * 
     * @return true if the start time is after the current time, false otherwise
     */
    public boolean isInFuture() {
        return startTime.isAfter(LocalDateTime.now());
    }
    
    /**
     * Checks if this time slot is within the next 24 hours.
     * 
     * @return true if the start time is between now and 24 hours from now, false otherwise
     */
    public boolean isWithin24Hours() {
        LocalDateTime now = LocalDateTime.now();
        return startTime.isAfter(now) && startTime.isBefore(now.plusHours(24));
    }
    
    /**
     * Returns a string representation of the time slot.
     * 
     * @return formatted string containing the start time
     */
    @Override
    public String toString() {
        return startTime.format(FORMATTER);
    }
}