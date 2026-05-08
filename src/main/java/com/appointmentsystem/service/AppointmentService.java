package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;

import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.VisitorRepository;

import io.github.cdimascio.dotenv.Dotenv;

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
    private List<AppointmentObserver> observers = new ArrayList<>();
     
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
    
    /*public AppointmentService(AppointmentRepository appointmentRepository, PropertyRepository propertyRepository) {
        this.appointmentRepository = appointmentRepository;
        this.propertyRepository = propertyRepository;
        this.visitorRepository = new VisitorRepository();
        
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");
        
        this.emailService = new EmailService(username, password);
    }
    */
    
    
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
		//this.appointmentRepository = appointmentRepository;

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
        notifyObservers(appointment, "CANCELLED");
    }
   
    
    public List<Appointment> getUpcomingAppointments() {
        return getAllAppointments().stream()
                .filter(a -> a.isFuture() && a.getStatus() == AppointmentStatus.CONFIRMED)
                .collect(Collectors.toList());
    }
    
    
    
    public void modifyAppointment(String appointmentId, TimeSlot newSlot) {
        Appointment app = appointmentRepository.findById(appointmentId);
        if (app == null) {
            throw new IllegalArgumentException("Appointment not found");
        }
        if (!app.isFuture()) {
            throw new IllegalStateException("Past appointment");
        }
        if (!newSlot.isAvailable()) {
            throw new IllegalStateException("Slot taken");
        }
        if (app.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot modify cancelled appointment");
        }
        app.getSlot().setAvailable(true);
        app.setSlot(newSlot);
        newSlot.setAvailable(false);
        appointmentRepository.update(app);
        notifyObservers(app, "MODIFIED");
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
        
        notifyObservers(appointment, "BOOKED");
        return appointment;
    }
    
   // public void setEmailService(EmailService emailService) {
      //  this.emailService = emailService;
    //}
    
    //public void setVisitorRepository(VisitorRepository visitorRepository) {
       // this.visitorRepository = visitorRepository;
   // }
    
    
    
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
    
    public void addObserver(AppointmentObserver o) {
        observers.add(o);
    }

    //public void removeObserver(AppointmentObserver o) {
       // observers.remove(o);
   // }

    private void notifyObservers(Appointment a, String eventType) {
        for (AppointmentObserver o : observers) {
            o.update(a, eventType);
        }
    }

    
    public List<AppointmentObserver> getObservers() {
        return observers;
    }
    
}
