package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Appointment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */

public class AppointmentRepository {
    
    private static List<Appointment> appointments = new ArrayList<>();
    
    /**
     * @param appointment the appointment to save
     */
    public void save(Appointment appointment) {
        if (appointment == null) return;
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
        }
    }
    
   
    
    /**
     * @param id the ID of the appointment to delete
     */
    public void deleteById(String id) {
        if (id == null) return;
        appointments.removeIf(a -> id.equals(a.getId()));
    }
    
    /**
     * @param id the appointment ID
     * @return the appointment if found, null otherwise
     */
    public Appointment findById(String id) {
        if (id == null) return null;
        return appointments.stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * @return all appointments
     */
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }
    
    /**
     * @param visitorId the visitor ID
     * @return appointments for the visitor
     */
    public List<Appointment> findByVisitorId(String visitorId) {
        if (visitorId == null) return new ArrayList<>();
        return appointments.stream()
                .filter(a -> visitorId.equals(a.getVisitorId()))
                .collect(Collectors.toList());
    }
    

    
    /**
     * @param appointment the appointment to update
     */
    public void update(Appointment appointment) {
        if (appointment == null) return;
        deleteById(appointment.getId());
        save(appointment);
    }
    

    
    

}