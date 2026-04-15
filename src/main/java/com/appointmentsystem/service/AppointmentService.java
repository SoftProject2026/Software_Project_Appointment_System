package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.VisitorRepository;

import io.github.cdimascio.dotenv.Dotenv;

import java.time.LocalDateTime;
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
public class AppointmentService {
    
    private AppointmentRepository appointmentRepository;
    private PropertyRepository propertyRepository;
    private VisitorRepository visitorRepository;
    
    private EmailService emailService;
    
    public AppointmentService() {
        this.appointmentRepository = new AppointmentRepository();
        this.propertyRepository = new PropertyRepository();
        this.visitorRepository = new VisitorRepository();
        
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");
        
        this.emailService = new EmailService(username, password);
    }
    
    public AppointmentService(AppointmentRepository appointmentRepository, PropertyRepository propertyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.propertyRepository = propertyRepository;
        this.visitorRepository = new VisitorRepository();
        
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");
        
        this.emailService = new EmailService(username, password);
    }
    
    
    public AppointmentService(AppointmentRepository appointmentRepository, 
            PropertyRepository propertyRepository, 
            VisitorRepository visitorRepository,
            EmailService emailService) {
			this.appointmentRepository = appointmentRepository;
			this.propertyRepository = propertyRepository;
			this.visitorRepository = visitorRepository;
			this.emailService = emailService;
    }
    
    
	//public AppointmentService(AppointmentRepository appointmentRepository) {
	//	this.appointmentRepository = appointmentRepository;

	//}

	public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }    
    
    public List<Appointment> getAppointmentsByVisitor(String visitorId) {
        return appointmentRepository.findByVisitorId(visitorId);
    }
    
    
///////////new function to test   
//    public List<Appointment> getAppointmentsByCompany(String companyId) {
//        return appointmentRepository.findByCompanyId(companyId);
//    }
////////////////

    public void viewCompanyAppointments(Company c) {
    	List<Appointment> allAppointments = appointmentRepository.findAll();
        boolean found = false;
        for (Appointment a : allAppointments) {
        	Property p = propertyRepository.findById(a.getPropertyId());
            if (p != null && p.getCompanyId().equals(c.getId())) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No appointments");
        }
//    	
//        List<Appointment> all = appointmentRepository.findAll();
//        int i = 0;
//        for (Appointment a : all) {
//            Property p = propertyRepository.findById(a.getPropertyId());
//            if (p != null && p.getCompanyId().equals(c.getId())) {
//                Visitor v = visitorRepository.findById(a.getVisitorId());
//                System.out.println(i + ". Visitor: " + v.getName()
//                    + " | Date: " + a.getSlot()
//                    + " | Type: " + a.getType()
//                    + " | Status: " + a.getStatus());
//                i++;
//            }
//        }
//        if (i == 0) {
//            System.out.println("No appointments");
//        }
    }


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
        if (app.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Cannot modify cancelled appointment");
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
        Appointment appointment = new Appointment(propertyId, visitorId, slot, type);
        
        slot.setAvailable(false);
        appointmentRepository.save(appointment);
        
        String subject = "Appointment Confirmation";
        String body = "Your appointment has been booked successfully.";
        Visitor v = visitorRepository.findById(visitorId);
        emailService.sendEmail(v.getEmail(), subject, body);;
        
        return appointment;
    }
    
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
    
    public void setVisitorRepository(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }
    
    
    
    public void viewAllAppointments() {
        List<Appointment> apps = appointmentRepository.findAll();

        if (apps.isEmpty()) {
            System.out.println("No appointments");
            return;
        }

        for (int i = 0; i < apps.size(); i++) {
            System.out.println(i + ". " + apps.get(i));
        }
    }

    
}