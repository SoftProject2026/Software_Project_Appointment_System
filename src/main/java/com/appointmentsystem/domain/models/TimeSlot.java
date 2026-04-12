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
    //public LocalDateTime getEndTime() { return endTime; }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }
    
    // public int getDuration() {
     //   return (int) java.time.Duration.between(startTime, endTime).toMinutes();
      //}
    
//    public boolean overlapsWith(TimeSlot other) {
//        if (other == null) return false;
//        return !(this.endTime.isBefore(other.startTime) || this.startTime.isAfter(other.endTime));
//    }
    
//    public boolean contains(LocalDateTime dateTime) {
//        return (dateTime.isEqual(startTime) || dateTime.isAfter(startTime)) && dateTime.isBefore(endTime);
//    }
    
    public boolean isInFuture() {
        return startTime.isAfter(LocalDateTime.now());
    }
    
    public boolean isWithin24Hours() {
        LocalDateTime now = LocalDateTime.now();
        return startTime.isAfter(now) && startTime.isBefore(now.plusHours(24));
    }
    
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TimeSlot timeSlot = (TimeSlot) o;
//        return Objects.equals(startTime, timeSlot.startTime) && Objects.equals(endTime, timeSlot.endTime);
//    }
    
//    @Override
//    public int hashCode() { 
//        return Objects.hash(startTime, endTime);
//    }
    
//    @Override
//    public String toString() {
//        return String.format("%s - %s", startTime.format(FORMATTER), endTime.format(FORMATTER));
//    }
    
//    public static TimeSlot createOneHourSlot(LocalDateTime start) {
//        return new TimeSlot(start, start.plusHours(1));
//    }
//    
//    public static TimeSlot createSlotWithDuration(LocalDateTime start, int minutes) {
//        return new TimeSlot(start, start.plusMinutes(minutes));
//    }
}