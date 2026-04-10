package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.User;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisitorService {

    //private AppointmentRepository appointmentRepository;
    private VisitorRepository visitorRepository = new VisitorRepository();
    
    
    public void signup(Visitor v) {
        if (visitorRepository.findByUsername(v.getUsername()) != null)
            throw new RuntimeException("Username exists");
        visitorRepository.save(v);
    }
    

    public Visitor login(String username, String password) {
        Visitor v = visitorRepository.findByUsername(username);

        if (v == null || !v.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials");

        return v;
    }
    
//    
//    public VisitorService() {
//        this.visitorRepository = new VisitorRepository();
//        this.appointmentRepository = new AppointmentRepository();
//    }
//    
//    public VisitorService(VisitorRepository userRepository, AppointmentRepository appointmentRepository) {
//        this.visitorRepository = userRepository;
//        this.appointmentRepository = appointmentRepository;
//    }
//    
//    
//    public void createVisitor(Visitor visitor) {
//        if (visitor == null) return;
//        visitorRepository.save(visitor);
//    }
//    
//    public Visitor getVisitorById(String id) {
//    	Visitor user = visitorRepository.findById(id);
//        return user;
//    }
//    
//    public Visitor getVisitorByUsername(String username) {
//    	Visitor user = visitorRepository.findByUsername(username);
//        return user instanceof Visitor ? (Visitor) user : null;
//    }
//    
//    public List<Visitor> getAllVisitors() {
//        return userRepository.findByRole("VISITOR").stream()
//                .map(u -> (Visitor) u)
//                .collect(Collectors.toList());
//    }
//    
//    
//    public void addAppointmentToVisitor(String visitorId, Appointment appointment) {
//        Visitor visitor = getVisitorById(visitorId);
//        if (visitor == null || appointment == null) return;
//        
//        appointmentRepository.save(appointment);
//        visitor.addAppointmentId(appointment.getId());
//        userRepository.update(visitor);
//    }
//    
//    public void cancelAppointment(String visitorId, String appointmentId) {
//        Visitor visitor = getVisitorById(visitorId);
//        if (visitor == null) return;
//        
//        Appointment appointment = appointmentRepository.findById(appointmentId);
//        if (appointment != null && visitor.hasAppointment(appointmentId)) {
//            appointment.cancel();
//            appointmentRepository.update(appointment);
//        }
//    }
//    
//    public List<Appointment> getVisitorAppointments(String visitorId) {
//        Visitor visitor = getVisitorById(visitorId);
//        if (visitor == null) return new ArrayList<>();
//        
//        return visitor.getAppointmentIds().stream()
//                .map(appointmentRepository::findById)
//                .filter(a -> a != null)
//                .collect(Collectors.toList());
//    }
//    
//    public List<Appointment> getUpcomingAppointments(String visitorId) {
//        return getVisitorAppointments(visitorId).stream()
//                .filter(a -> a.isFuture() && a.getStatus() == AppointmentStatus.CONFIRMED)
//                .collect(Collectors.toList());
//    }
//    
//    public List<Appointment> getCompletedAppointments(String visitorId) {
//        return getVisitorAppointments(visitorId).stream()
//                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
//                .collect(Collectors.toList());
//    }
//    
//    public List<Appointment> getCancelledAppointments(String visitorId) {
//        return getVisitorAppointments(visitorId).stream()
//                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
//                .collect(Collectors.toList());
//    }
//    
//    public List<Appointment> getAppointmentsByProperty(String visitorId, String propertyId) {
//        return getVisitorAppointments(visitorId).stream()
//                .filter(a -> a.getPropertyId().equals(propertyId))
//                .collect(Collectors.toList());
//    }
//    
//    public Appointment findAppointmentById(String appointmentId) {
//        return appointmentRepository.findById(appointmentId);
//    }
//    
//    public boolean hasAppointment(String visitorId, String appointmentId) {
//        Visitor visitor = getVisitorById(visitorId);
//        return visitor != null && visitor.hasAppointment(appointmentId);
//    }
//    
//    public boolean hasUpcomingAppointments(String visitorId) {
//        return !getUpcomingAppointments(visitorId).isEmpty();
//    }
//    
//    
//    
//    public int getTotalAppointmentsCount(String visitorId) {
//        Visitor visitor = getVisitorById(visitorId);
//        return visitor != null ? visitor.getTotalAppointmentsCount() : 0;
//    }
//    
//    public int getUpcomingAppointmentsCount(String visitorId) {
//        return getUpcomingAppointments(visitorId).size();
//    }
//    
//    public int getCompletedAppointmentsCount(String visitorId) {
//        return getCompletedAppointments(visitorId).size();
//    }
//    
//    public int getCancelledAppointmentsCount(String visitorId) {
//        return getCancelledAppointments(visitorId).size();
//    }
}