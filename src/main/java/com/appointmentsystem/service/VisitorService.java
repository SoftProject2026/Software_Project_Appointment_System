package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Visitor;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.TimeSlot;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.AppointmentType;
import com.appointmentsystem.persistence.VisitorRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class VisitorService {

    //private AppointmentRepository appointmentRepository;
	private Scanner scanner;
	private AppointmentService appointmentService = new AppointmentService();
    private VisitorRepository visitorRepository = new VisitorRepository();
    private PropertyService propertyService = new PropertyService();
	private AppointmentRepository appointmentRepository;
    
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
    
    public void logout(Visitor visitor) {
        if (visitor == null) {
            System.out.println("No user is currently logged in.");
            return;
        }
        System.out.println("Goodbye, " + visitor.getName() + "! You have been logged out successfully.");
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
            System.out.println(i + ". " + slots.get(i));
        }

        System.out.print("Choose slot index: ");
        int sIndex = scanner.nextInt();

        TimeSlot selectedSlot = slots.get(sIndex);

        AppointmentType type ;//= AppointmentType.IN_PERSON; //needs work
        
        System.out.println("Appointment Type:");
        AppointmentType[] types = AppointmentType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println(i + ". " + types[i]);
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
        System.out.println("Appointment booked successfully!");
        System.out.println(appointment);
    }
    
    public  List<Appointment> viewMyAppointments(Visitor visitor) {
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