package com.appointmentsystem.domain.models;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public final class TimeSlot {
    private  LocalDateTime startTime;
    private  LocalDateTime endTime;
    
    private boolean isAvailable = true;
    
    public TimeSlot(LocalDateTime startTime) {
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create a time slot in the past");
        }
        this.startTime = startTime;
        this.isAvailable = true;
    }
    
    public LocalDateTime getStartTime() { return startTime; }
    
    
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
   
    
    @Override
    public String toString() {
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return startTime.format(formatter);
    }
    

}