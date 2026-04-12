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
    
   
    public void save(Appointment appointment) {
        if (appointment == null) return;
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
        }
    }
    
    public void delete(Appointment appointment) {
        if (appointment == null) return;
        appointments.remove(appointment);
    }
    
    public void deleteById(String id) {
        if (id == null) return;
        appointments.removeIf(a -> id.equals(a.getId()));
    }
    
    public Appointment findById(String id) {
        if (id == null) return null;
        return appointments.stream()
                .filter(a -> id.equals(a.getId()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Appointment> findAll() {
        return new ArrayList<>(appointments);
    }
    

    public List<Appointment> findByVisitorId(String visitorId) {
        if (visitorId == null) return new ArrayList<>();
        return appointments.stream()
                .filter(a -> visitorId.equals(a.getVisitorId()))
                .collect(Collectors.toList());
    }
    
    public List<Appointment> findByPropertyId(String propertyId) {
        if (propertyId == null) return new ArrayList<>();
        return appointments.stream()
                .filter(a -> propertyId.equals(a.getPropertyId()))
                .collect(Collectors.toList());
    }
    
//    public List<Appointment> findByCompanyId(String companyId) {
//        if (companyId == null) return new ArrayList<>();
//        return appointments.stream()
//                .filter(a -> companyId.equals(a.getCompanyId()))
//                .collect(Collectors.toList());
//    }
    
    public void update(Appointment appointment) {
        if (appointment == null) return;
        deleteById(appointment.getId());
        save(appointment);
    }
    
    public boolean exists(String id) {
        if (id == null) return false;
        return appointments.stream().anyMatch(a -> id.equals(a.getId()));
    }
    
    public void clear() {
        appointments.clear();
    }
    
    public int count() {
        return appointments.size();
    }
}