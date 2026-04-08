package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
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
    
    
    public void createAppointment(Appointment appointment) {
        if (appointment == null) return;
        appointmentRepository.save(appointment);
    }
    
    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }
    
    public void deleteAppointment(String id) {
        appointmentRepository.deleteById(id);
    }
    
    
    
    public List<Appointment> getAppointmentsByCompany(String companyId) {
        return appointmentRepository.findByCompanyId(companyId);
    }
    
    public List<Appointment> getAppointmentsByVisitor(String visitorId) {
        return appointmentRepository.findByVisitorId(visitorId);
    }
    
    public List<Appointment> getAppointmentsByProperty(String propertyId) {
        return appointmentRepository.findByPropertyId(propertyId);
    }
    
    //public List<Appointment> getAppointmentsByStatus(AppointmentStatus status) {
      //  return getAllAppointments().stream()
       //         .filter(a -> a.getStatus() == status)
        //        .collect(Collectors.toList());
    //}
    
    public List<Appointment> getTodayAppointments() {
        return getAllAppointments().stream()
                .filter(Appointment::isToday)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getUpcomingAppointments() {
        return getAllAppointments().stream()
                .filter(a -> a.isInFuture() && a.getStatus() == AppointmentStatus.SCHEDULED)
                .collect(Collectors.toList());
    }
    
    /*public List<Appointment> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end) {
        return getAllAppointments().stream()
                .filter(a -> a.getStartTime().isAfter(start) && a.getEndTime().isBefore(end))
                .collect(Collectors.toList());
    }*/
    
    
    
    public void confirmAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.confirm();
            appointmentRepository.update(appointment);
        }
    }
    
    public void cancelAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.cancel();
            appointmentRepository.update(appointment);
        }
    }
    
    public void completeAppointment(String appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.complete();
            appointmentRepository.update(appointment);
        }
    }
    
    /*public void markVisitorAttendance(String appointmentId, boolean attended) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.setVisitorAttended(attended);
            appointmentRepository.update(appointment);
        }
    }
    
    public void addNotes(String appointmentId, String notes) {
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.setNotes(notes);
            appointmentRepository.update(appointment);
        }
    }*/
    
    public boolean isAppointmentAvailable(String propertyId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Appointment> propertyAppointments = getAppointmentsByProperty(propertyId);
        return propertyAppointments.stream().noneMatch(a -> 
            a.getStatus() != AppointmentStatus.CANCELLED &&
            (startTime.isBefore(a.getEndTime()) && endTime.isAfter(a.getStartTime()))
        );
    }
    
    
    
    public int getTotalAppointmentsCount() {
        return appointmentRepository.count();
    }
    
    /*public int getAppointmentsCountByStatus(AppointmentStatus status) {
        return (int) getAppointmentsByStatus(status).size();
    }
    
    public int getTodayAppointmentsCount() {
        return getTodayAppointments().size();
    }*/
}