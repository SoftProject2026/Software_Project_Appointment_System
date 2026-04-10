package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {
    
    private AppointmentRepository appointmentRepository;
    
    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepository();
    }
    
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }    
    
    public List<Appointment> getAppointmentsByVisitor(String visitorId) {
        return appointmentRepository.findByVisitorId(visitorId);
    }

    public List<Appointment> getUpcomingAppointments() {
        return getAllAppointments().stream()
                .filter(a -> a.isFuture() && a.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }
    
//    public void completeAppointment(String appointmentId) {
//        Appointment appointment = appointmentRepository.findById(appointmentId);
//        if (appointment != null) {
//            appointment.complete();
//            appointmentRepository.update(appointment);
//        }
//    }
    
    
    public void cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found");
        }
        if (!appointment.isFuture()) {
            throw new IllegalStateException("Cannot cancel past appointment");
        }
        appointment.cancel();
        appointment.getSlot().setAvailable(true);
        appointmentRepository.update(appointment);
    }
    
    public void modifyAppointment(String appointmentId, TimeSlot newSlot) {
        Appointment app = appointmentRepository.findById(appointmentId);
        if (app == null) {
            throw new RuntimeException("Appointment not found");
        }
        if (!app.isFuture()) {
            throw new RuntimeException("Past appointment");
        }
        if (!newSlot.isAvailable()) {
            throw new RuntimeException("Slot taken");
        }
        app.getSlot().setAvailable(true);
        app.setSlot(newSlot);
        newSlot.setAvailable(false);
        appointmentRepository.update(app);
    }
    
    
    public Appointment bookAppointment(String propertyId, String visitorId, TimeSlot slot, AppointmentType type) {
        if (slot == null) {
            throw new IllegalArgumentException("Invalid slot");
        }
        if (!slot.isAvailable()) {
            throw new IllegalStateException("Slot already booked");
        }
        if (slot.getDurationInMinutes() > 30) {
            throw new IllegalStateException("Max duration is 30 minutes");
        }
        Appointment appointment = new Appointment(propertyId, visitorId, slot, type);
        
        slot.setAvailable(false);
        //appointment.confirm();
        appointmentRepository.save(appointment);
        return appointment;
    }
    
    
    
    public int getTotalAppointmentsCount() {
        return appointmentRepository.count();
    }
    
}