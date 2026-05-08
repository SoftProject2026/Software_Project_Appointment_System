package com.appointmentsystem.domain.models;

import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;

import java.util.UUID;
/**
 * Represents an appointment booked by a visitor for a specific property.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Appointment {
    private String id;
    private String propertyId;
    private String visitorId;
    private AppointmentStatus status;
    private AppointmentType type;
    private TimeSlot slot;
    /**
     * 
     * @param propertyId the ID of the property being booked
     * @param visitorId the ID of the visitor making the booking
     * @param slot the time slot for the appointment
     * @param type the type of appointment (determines duration)
     */
    public Appointment(String propertyId, String visitorId,TimeSlot slot, AppointmentType type) {
        this.id = UUID.randomUUID().toString();
        this.propertyId = propertyId;
        this.visitorId = visitorId;
        this.slot = slot;
        this.type = type;
        this.status = AppointmentStatus.CONFIRMED;
    }
    /**
     *  Default constructor for frameworks that require no-arg constructor.
     */
    public Appointment() {
        this.id = UUID.randomUUID().toString();
        this.propertyId = "";
        this.visitorId = "";
        this.slot = null;
        this.type = null;
        this.status = AppointmentStatus.CONFIRMED;
    }
    
    
/**
 * Returns the time slot of the appointment.
 * @return the TimeSlot object containing start time and availability
 */
	public TimeSlot getSlot() { return slot; }
	/**
	 * Sets the time slot for the appointment.
	 * @param slot the new TimeSlot for the appointment
	 */
    public void setSlot(TimeSlot slot) {this.slot = slot;}
    
    /**
     * Returns the unique identifier of the appointment.
     * @return the appointment ID (UUID format)
     */
    public String getId() { return id; }
    /**
     * Returns the property ID associated with this appointment.
     * @return the property ID
     */
    public String getPropertyId() { return propertyId; }
    
   
    
    /**
     * Returns the visitor ID who booked this appointment.
     * @return the visitor ID
     */
    public String getVisitorId() { return visitorId; }
    
    public AppointmentType getType() { return type; }
    
    /**
     * Returns the current status of the appointment.
     * 
     * @return the AppointmentStatus (CONFIRMED, CANCELLED, COMPLETED)
     */
    public AppointmentStatus getStatus() { return status; }
   
    
    /**
     * Cancels the appointment by setting its status to CANCELLED.
     */
    public void cancel() {
        this.status = AppointmentStatus.CANCELLED;
    }
    
    
    /**
     *Checks if the appointment is scheduled for a future time.
     * 
     * @return true if the appointment's start time is after the current time,
     *         false otherwise
     */
    public boolean isFuture() {
        return slot.getStartTime().isAfter(java.time.LocalDateTime.now());
    }
    
    /**
     * Returns a string representation of the appointment.
     * The format includes propertyId, visitorId, and status.
     * 
     * @return a formatted string containing appointment information
     */
    @Override
    public String toString() {
        return "Date: " + slot +" | Type: " + type +" | Status: " + status;
    }
}