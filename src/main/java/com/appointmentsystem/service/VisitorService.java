package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.User;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.persistence.UserRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisitorService {
    
    private UserRepository userRepository;
    private AppointmentRepository appointmentRepository;
    
    public VisitorService() {
        this.userRepository = new UserRepository();
        this.appointmentRepository = new AppointmentRepository();
    }
    
    public VisitorService(UserRepository userRepository, AppointmentRepository appointmentRepository) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }
    
    
    public void createVisitor(Visitor visitor) {
        if (visitor == null) return;
        userRepository.save(visitor);
    }
    
    public Visitor getVisitorById(String id) {
        User user = userRepository.findById(id);
        return user instanceof Visitor ? (Visitor) user : null;
    }
    
    public Visitor getVisitorByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user instanceof Visitor ? (Visitor) user : null;
    }
    
    public List<Visitor> getAllVisitors() {
        return userRepository.findByRole("VISITOR").stream()
                .map(u -> (Visitor) u)
                .collect(Collectors.toList());
    }
    
    public void updateVisitor(Visitor visitor) {
        userRepository.update(visitor);
    }
    
    public void deleteVisitor(String id) {
        userRepository.delete(id);
    }
    
    
    
    public void addAppointmentToVisitor(String visitorId, Appointment appointment) {
        Visitor visitor = getVisitorById(visitorId);
        if (visitor == null || appointment == null) return;
        
        appointmentRepository.save(appointment);
        visitor.addAppointmentId(appointment.getId());
        userRepository.update(visitor);
    }
    
    public void cancelAppointment(String visitorId, String appointmentId) {
        Visitor visitor = getVisitorById(visitorId);
        if (visitor == null) return;
        
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null && visitor.hasAppointment(appointmentId)) {
            appointment.cancel();
            appointmentRepository.update(appointment);
        }
    }
    
    public List<Appointment> getVisitorAppointments(String visitorId) {
        Visitor visitor = getVisitorById(visitorId);
        if (visitor == null) return new ArrayList<>();
        
        return visitor.getAppointmentIds().stream()
                .map(appointmentRepository::findById)
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getUpcomingAppointments(String visitorId) {
        return getVisitorAppointments(visitorId).stream()
                .filter(a -> a.isInFuture() && a.getStatus() == AppointmentStatus.SCHEDULED)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getCompletedAppointments(String visitorId) {
        return getVisitorAppointments(visitorId).stream()
                .filter(a -> a.getStatus() == AppointmentStatus.COMPLETED)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getCancelledAppointments(String visitorId) {
        return getVisitorAppointments(visitorId).stream()
                .filter(a -> a.getStatus() == AppointmentStatus.CANCELLED)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getAppointmentsByProperty(String visitorId, String propertyId) {
        return getVisitorAppointments(visitorId).stream()
                .filter(a -> a.getPropertyId().equals(propertyId))
                .collect(Collectors.toList());
    }
    
    public Appointment findAppointmentById(String appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
    
    public boolean hasAppointment(String visitorId, String appointmentId) {
        Visitor visitor = getVisitorById(visitorId);
        return visitor != null && visitor.hasAppointment(appointmentId);
    }
    
    public boolean hasUpcomingAppointments(String visitorId) {
        return !getUpcomingAppointments(visitorId).isEmpty();
    }
    
    
    
    public int getTotalAppointmentsCount(String visitorId) {
        Visitor visitor = getVisitorById(visitorId);
        return visitor != null ? visitor.getTotalAppointmentsCount() : 0;
    }
    
    public int getUpcomingAppointmentsCount(String visitorId) {
        return getUpcomingAppointments(visitorId).size();
    }
    
    public int getCompletedAppointmentsCount(String visitorId) {
        return getCompletedAppointments(visitorId).size();
    }
    
    public int getCancelledAppointmentsCount(String visitorId) {
        return getCancelledAppointments(visitorId).size();
    }
}