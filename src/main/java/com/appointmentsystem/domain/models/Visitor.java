package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;

public class Visitor extends User {
    private String phone;
    private String preferredLocation;
    private List<String> appointmentIds;
    
    public Visitor(String id, String name, String username, String email, String password, String phone) {
        super(id, name, username, email, password);
        this.phone = phone;
        this.appointmentIds = new ArrayList<>();
    }
    
    public Visitor(String id, String name, String username, String email, String password,
                   String phone, String preferredLocation) {
        super(id, name, username, email, password);
        this.phone = phone;
        this.preferredLocation = preferredLocation;
        this.appointmentIds = new ArrayList<>();
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }
    
    public List<String> getAppointmentIds() { 
        return new ArrayList<>(appointmentIds); 
    }
    
    public void addAppointmentId(String appointmentId) {
        if (!appointmentIds.contains(appointmentId)) {
            appointmentIds.add(appointmentId);
        }
    }
    
    public boolean removeAppointmentId(String appointmentId) {
        return appointmentIds.removeIf(id -> id.equals(appointmentId));
    }
    
    public boolean hasAppointment(String appointmentId) {
        return appointmentIds.contains(appointmentId);
    }
    
    public int getTotalAppointmentsCount() {
        return appointmentIds.size();
    }
    
    // Business logic methods that don't need full Appointment objects
    public boolean hasUpcomingAppointments() {
        // This will be checked in Service layer with actual appointments
        return !appointmentIds.isEmpty();
    }
    
    @Override
    public String getRole() {
        return "VISITOR";
    }
    
    @Override
    public String toString() {
        return "Visitor{id='" + getId() + "', name='" + getName() + "', phone='" + phone + "', appointments=" + appointmentIds.size() + "}";
    }
}

/*
package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;

public class Visitor extends User {
    private String phone;
    private String preferredLocation;
    private double minBudget;
    private double maxBudget;
    private List<Appointment> myAppointments;
    
    public Visitor(String id, String name, String username, String email, String password, String phone) {
        super(id, name, username, email, password);
        this.phone = phone;
        this.myAppointments = new ArrayList<>();
    }
    
    public Visitor(String id, String name, String username, String email, String password,
                   String phone, String preferredLocation, double minBudget, double maxBudget) {
        super(id, name, username, email, password);
        this.phone = phone;
        this.preferredLocation = preferredLocation;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.myAppointments = new ArrayList<>();
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }
    
    public double getMinBudget() { return minBudget; }
    public void setMinBudget(double minBudget) { this.minBudget = minBudget; }
    
    public double getMaxBudget() { return maxBudget; }
    public void setMaxBudget(double maxBudget) { this.maxBudget = maxBudget; }
    
    public List<Appointment> getMyAppointments() { 
        return new ArrayList<>(myAppointments); 
    }
    
    public void addAppointment(Appointment appointment) {
        if (!myAppointments.contains(appointment)) {
            myAppointments.add(appointment);
        }
    }
    
    public boolean cancelAppointment(String appointmentId) {
        for (Appointment app : myAppointments) {
            if (app.getId().equals(appointmentId)) {
                app.setStatus(AppointmentStatus.CANCELLED);
                return true;
            }
        }
        return false;
    }
    
    public Appointment findAppointmentById(String appointmentId) {
        for (Appointment app : myAppointments) {
            if (app.getId().equals(appointmentId)) {
                return app;
            }
        }
        return null;
    }
    
    public List<Appointment> getUpcomingAppointments() {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : myAppointments) {
            if (app.isInFuture() && app.getStatus() == AppointmentStatus.SCHEDULED) {
                result.add(app);
            }
        }
        return result;
    }
    
    public List<Appointment> getCompletedAppointments() {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : myAppointments) {
            if (app.getStatus() == AppointmentStatus.COMPLETED) {
                result.add(app);
            }
        }
        return result;
    }
    
    public List<Appointment> getCancelledAppointments() {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : myAppointments) {
            if (app.getStatus() == AppointmentStatus.CANCELLED) {
                result.add(app);
            }
        }
        return result;
    }
    
    public List<Appointment> getAppointmentsByProperty(String propertyId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : myAppointments) {
            if (app.getPropertyId().equals(propertyId)) {
                result.add(app);
            }
        }
        return result;
    }
    
    public int getTotalAppointmentsCount() {
        return myAppointments.size();
    }
    
    public int getUpcomingAppointmentsCount() {
        return getUpcomingAppointments().size();
    }
    
    public int getCompletedAppointmentsCount() {
        return getCompletedAppointments().size();
    }
    
    public int getCancelledAppointmentsCount() {
        return getCancelledAppointments().size();
    }
    
    public boolean hasAppointment(String appointmentId) {
        for (Appointment app : myAppointments) {
            if (app.getId().equals(appointmentId)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasUpcomingAppointments() {
        return !getUpcomingAppointments().isEmpty();
    }
    
    @Override
    public String getRole() {
        return "VISITOR";
    }
    
    @Override
    public String toString() {
        return "Visitor{id='" + getId() + "', name='" + getName() + "', phone='" + phone + "', appointments=" + myAppointments.size() + "}";
    }
}*/