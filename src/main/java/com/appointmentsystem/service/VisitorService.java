package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * Service for visitor operations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class VisitorService {

    private Scanner scanner;
    private AppointmentService appointmentService = new AppointmentService();
    private VisitorRepository visitorRepository = new VisitorRepository();
    private PropertyService propertyService = new PropertyService();
    
    /**
     * @param visitorRepository the visitor repository
     * @param propertyService the property service
     * @param appointmentService the appointment service
     * @param scanner the input scanner
     */
    public VisitorService(VisitorRepository visitorRepository,
            PropertyService propertyService,
            AppointmentService appointmentService,
            Scanner scanner) {
        this.visitorRepository = visitorRepository;
        this.propertyService = propertyService;
        this.appointmentService = appointmentService;
        this.scanner = scanner;
    }
    
    public VisitorService() {
        this.visitorRepository = new VisitorRepository();
        this.propertyService = new PropertyService();
        this.appointmentService = new AppointmentService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * @param v the visitor to register
     * @throws RuntimeException if username exists or email invalid
     */
    public void signup(Visitor v) {
        if (visitorRepository.findByUsername(v.getUsername()) != null)
            throw new RuntimeException("Username exists");
        if(isValidEmail(v.getEmail()))
            visitorRepository.save(v);
        else throw new RuntimeException("Invalid Email");
    }
    
    /**
     * @param username the username
     * @param password the password
     * @return the authenticated visitor
     * @throws RuntimeException if credentials invalid
     */
    public Visitor login(String username, String password) {
        Visitor v = visitorRepository.findByUsername(username);
        if (v == null || !v.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials");
        return v;
    }
    
    /**
     * @param visitor the visitor to logout
     */
    public void logout(Visitor visitor) {
        if (visitor == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("Goodbye, " + visitor.getName() + "! You have been logged out successfully.");
    }
    
    /**
     * @param visitor the visitor booking the appointment
     */
    public void bookAppointment(Visitor visitor) {
        // ... existing code ...
    }
    
    /**
     * @param visitor the visitor
     * @return list of appointments
     */
    public List<Appointment> viewMyAppointments(Visitor visitor) {
        List<Appointment> apps = appointmentService.getAppointmentsByVisitor(visitor.getId());
        if (apps.isEmpty()) {
            System.out.println("No appointments");
            return apps;
        }
        for (int i = 0; i < apps.size(); i++) {
            System.out.println(i + ". " + apps.get(i));
        }
        return apps;
    }
    
    /**
     * @param visitor the visitor cancelling the appointment
     */
    public void cancelAppointment(Visitor visitor) {
        List<Appointment> apps = viewMyAppointments(visitor);
        if (apps.isEmpty()) {
            System.out.println("Nothing to do");
            return;
        }
        System.out.print("Choose appointment index to cancel: ");
        int index = scanner.nextInt();
        Appointment selected = apps.get(index);
        try {
            appointmentService.cancelAppointment(selected.getId());
            System.out.println("Cancelled successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * @param visitor the visitor modifying the appointment
     */
    public void modifyAppointment(Visitor visitor) {
        List<Appointment> apps = viewMyAppointments(visitor);
        if (apps.isEmpty()) {
            System.out.println("Nothing to do");
            return;
        }
        System.out.print("Choose appointment index: ");
        int index = scanner.nextInt();
        Appointment selected = apps.get(index);
        Property property = propertyService.getPropertyById(selected.getPropertyId());
        List<TimeSlot> slots = property.getAvailableSlots();
        if (slots.isEmpty()) {
            System.out.println("No available slots");
            return;
        }
        System.out.println("Available Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println(i + ". " + slots.get(i));
        }
        System.out.print("Choose new slot: ");
        int sIndex = scanner.nextInt();
        TimeSlot newSlot = slots.get(sIndex);
        try {
            appointmentService.modifyAppointment(selected.getId(), newSlot);
            System.out.println("Modified successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * @param email the email to validate
     * @return true if valid
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        if (!email.contains("@")) return false;
        if (!email.contains(".")) return false;
        if (email.startsWith("@") || email.endsWith("@")) return false;
        return true;
    }
}