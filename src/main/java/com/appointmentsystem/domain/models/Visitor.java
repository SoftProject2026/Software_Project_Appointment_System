package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class Visitor{
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    
    public Visitor(String id, String name, String username,String email, String password, String phone) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
    }
    
    /*public Visitor() {
    	
    }*/
    
    public String getId(){ return id; }
    public String getName(){ return name; }
    
    public String getUsername(){ return username; }
    
    public String getPassword(){ return password; }
    
    public String getEmail(){ return email; }
    
    public String getVisitorName(){return name;}
    public void setVisitorName(String companyName) {this.name = companyName; }
    
    public String getPhone(){ return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
 
//    public List<String> getAppointmentIds() { 
//        return new ArrayList<>(appointmentIds); 
//    }
//    
//    public void addAppointmentId(String appointmentId) {
//        if (!appointmentIds.contains(appointmentId)) {
//            appointmentIds.add(appointmentId);
//        }
//    }
//    
//    public boolean removeAppointmentId(String appointmentId) {
//        return appointmentIds.removeIf(id -> id.equals(appointmentId));
//    }
//    
//    public boolean hasAppointment(String appointmentId) {
//        return appointmentIds.contains(appointmentId);
//    }
//    
//    public int getTotalAppointmentsCount() {
//        return appointmentIds.size();
//    }
//    
//    // Business logic methods that don't need full Appointment objects
//    public boolean hasUpcomingAppointments() {
//        // This will be checked in Service layer with actual appointments
//        return !appointmentIds.isEmpty();
//    }
//    
    
    @Override
    public String toString() {
        return "Visitor{ name='" + getVisitorName() + "', phone='" + phone + "}";
    }
}

