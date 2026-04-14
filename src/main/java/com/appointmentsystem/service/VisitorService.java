package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;


import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.persistence.AppointmentRepository;
import com.appointmentsystem.persistence.CompanyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class VisitorService {

	private Scanner scanner;
	private AppointmentService appointmentService = new AppointmentService();
    private VisitorRepository visitorRepository = new VisitorRepository();
    private PropertyService propertyService = new PropertyService();
	
    private CompanyRepository companyRepository = new CompanyRepository();
    
	public VisitorService(VisitorRepository visitorRepository,
            PropertyService propertyService,
            AppointmentService appointmentService,
            CompanyRepository companyRepository,
            Scanner scanner) {
			this.visitorRepository = visitorRepository;
			this.propertyService = propertyService;
			this.appointmentService = appointmentService;
			this.companyRepository = companyRepository;
			this.scanner = scanner;
	}
	

	public VisitorService() {
		this.visitorRepository = new VisitorRepository();
		this.propertyService = new PropertyService();
		this.appointmentService = new AppointmentService();
		this.companyRepository = new CompanyRepository();
		this.scanner = new Scanner(System.in);
	}

	public void signup(Visitor v) {
        if (visitorRepository.findByUsername(v.getUsername()) != null)
            throw new RuntimeException("Username exists");
        if(isValidEmail(v.getEmail()))
        	visitorRepository.save(v);
        else throw new RuntimeException("Invalid Email");
    }
    

    public Visitor login(String username, String password) {
        Visitor v = visitorRepository.findByUsername(username);

        if (v == null || !v.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials");

        return v;
    }
    
    public void logout(Visitor visitor) {
        if (visitor == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("\nGoodbye, " + visitor.getName() + "! You have been logged out successfully.");
    }
    
    public void bookAppointment(Visitor visitor) {
        List<Property> properties = propertyService.getAllProperties();
        if (properties.isEmpty()) {
            System.out.println("No properties available");
            return;
        }
        System.out.println("Available Properties:");
        for (int i = 0; i < properties.size(); i++) {
            System.out.println(i + ". " + properties.get(i));
        }

        System.out.print("Choose property index: ");
        int pIndex = scanner.nextInt();

        Property selectedProperty = properties.get(pIndex);
        
        List<TimeSlot> slots = selectedProperty.getAvailableSlots();

        if (slots.isEmpty()) {
            System.out.println("No available slots");
            return;
        }
        System.out.println("Available Slots:");
        for (int i = 0; i < slots.size(); i++) {
        	System.out.println(i + ". " + slots.get(i).getStartTime());
        }

        System.out.print("Choose slot index: ");
        int sIndex = scanner.nextInt();

        TimeSlot selectedSlot = slots.get(sIndex);
        LocalDateTime startTime = selectedSlot.getStartTime();
        
        AppointmentType type ;
        System.out.println("Appointment Type:");        
        AppointmentType[] types = AppointmentType.values();
        for (int i = 0; i < types.length; i++) {
        	System.out.printf("%d. %s (%d minutes)%n", 
                    i, types[i], types[i].getDurationMinutes());
        }
            
        System.out.print("Choose type: ");
        int typeIndex = scanner.nextInt();

        if (typeIndex < 0 || typeIndex >= types.length) {
            System.out.println("Invalid choice, defaulting to IN_PERSON");
            type = AppointmentType.IN_PERSON;
        } else {
            type = types[typeIndex];
        }
                
        Appointment appointment = appointmentService.bookAppointment(
                selectedProperty.getId(),
                visitor.getId(),
                selectedSlot,
                type
        );        
        System.out.println("Appointment booked successfully! Status: " + appointment.getStatus());
    }
    
 ///////////// edited   
    public  List<Appointment> viewMyAppointments(Visitor visitor) {
        List<Appointment> apps = appointmentService.getAppointmentsByVisitor(visitor.getId());
        if (apps.isEmpty()) {
            System.out.println("No appointments");
            return apps;
        }
        for (int i = 0; i < apps.size(); i++) {
            Appointment a = apps.get(i);

            Property p = propertyService.getPropertyById(a.getPropertyId());
            if(p==null) continue;
            Company company = companyRepository.findById(p.getCompanyId());
            Visitor v = visitorRepository.findById(a.getVisitorId());
            if(company != null) {
                System.out.println(i + ". Company: " + company.getCompanyName()
                + " | Visitor: " + v.getName()
                + " | Status: " + a.getStatus());
            }

        }
        return apps;
    }
    
    
    public void cancelAppointment(Visitor visitor) {
    	List<Appointment> apps = viewMyAppointments(visitor);
    	if (apps.isEmpty()) {
    	    System.out.println("Nothing to do");
    	    return;
    	}
        System.out.print("\nChoose appointment index to cancel: ");
        int index = scanner.nextInt();

        Appointment selected = apps.get(index);
        try {
            appointmentService.cancelAppointment(selected.getId());
            System.out.println("\nCancelled successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void modifyAppointment(Visitor visitor) {
    	List<Appointment> apps = viewMyAppointments(visitor);
    	if (apps.isEmpty()) {
    	    System.out.println("Nothing to do");
    	    return;
    	}

        System.out.print("\nChoose appointment index: ");
        int index = scanner.nextInt();

        Appointment selected = apps.get(index);

        Property property = propertyService.getPropertyById(selected.getPropertyId());

        List<TimeSlot> slots = property.getAvailableSlots();

        if (slots.isEmpty()) {
            System.out.println("No available slots");
            return;
        }

        System.out.println("\nAvailable Slots:");
        for (int i = 0; i < slots.size(); i++) {
            System.out.println(i + ". " + slots.get(i));
        }

        System.out.print("\nChoose new slot: ");
        int sIndex = scanner.nextInt();

        TimeSlot newSlot = slots.get(sIndex);

        try {
            appointmentService.modifyAppointment(selected.getId(), newSlot);
            System.out.println("Modified successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        if (!email.contains(".")) {
            return false;
        }
        if (email.startsWith("@") || email.endsWith("@")) {
            return false;
        }
        return true;
    }
    

}