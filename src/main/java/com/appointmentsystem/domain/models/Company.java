package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.PropertyStatus;

public class Company extends User {
    private String companyName;
    private String commercialRegister;
    private String address;
    private List<String> propertyIds;
    private List<String> appointmentIds;
    private boolean isActive;
    private boolean isVerified;
    private Log log;
    
    public Company(String id, String companyName, String username, String email,
                   String password, String commercialRegister) {
        super(id, companyName, username, email, password);
        this.companyName = companyName;
        this.commercialRegister = commercialRegister;
        this.propertyIds = new ArrayList<>();
        this.appointmentIds = new ArrayList<>();
        this.log = new Log();
        this.isActive = true;
        this.isVerified = false;
    }

    public Company(String id, String companyName, String username, String email,
                   String password, String commercialRegister,
                   String address, int establishedYear) {
        super(id, companyName, username, email, password);
        this.companyName = companyName;
        this.commercialRegister = commercialRegister;
        this.address = address;
        this.propertyIds = new ArrayList<>();
        this.appointmentIds = new ArrayList<>();
        this.log = new Log();
        this.isActive = true;
        this.isVerified = false;
    }

    @Override
    public String getRole() {
        return "COMPANY";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) { 
        this.companyName = companyName; 
    }

    public String getCommercialRegister() { 
        return commercialRegister; 
    }
    
    public void setCommercialRegister(String commercialRegister) { 
        this.commercialRegister = commercialRegister; 
    }

    public String getAddress() { 
        return address; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }

    public boolean isActive() { 
        return isActive; 
    }
    
    public void setActive(boolean active) { 
        isActive = active; 
    }

    public boolean isVerified() { 
        return isVerified; 
    }
    
    public void setVerified(boolean verified) { 
        isVerified = verified; 
    }

    public List<String> getPropertyIds() {
        return new ArrayList<>(propertyIds);
    }

    public List<String> getAppointmentIds() {
        return new ArrayList<>(appointmentIds);
    }

    public void addPropertyId(String propertyId) {
        if (!propertyIds.contains(propertyId)) {
            propertyIds.add(propertyId);
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "ADD_PROPERTY",
                "PROPERTY",
                propertyId,
                "Added new property with ID: " + propertyId
            );
        }
    }

    public boolean removePropertyId(String propertyId) {
        boolean removed = propertyIds.removeIf(id -> id.equals(propertyId));
        if (removed) {
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "REMOVE_PROPERTY",
                "PROPERTY",
                propertyId,
                "Removed property with ID: " + propertyId
            );
        }
        return removed;
    }

    public boolean containsProperty(String propertyId) {
        return propertyIds.contains(propertyId);
    }

    public int getPropertiesCount() {
        return propertyIds.size();
    }

    public void addAppointmentId(String appointmentId) {
        if (!appointmentIds.contains(appointmentId)) {
            appointmentIds.add(appointmentId);
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "ADD_APPOINTMENT",
                "APPOINTMENT",
                appointmentId,
                "New appointment scheduled with ID: " + appointmentId
            );
        }
    }

    public boolean removeAppointmentId(String appointmentId) {
        boolean removed = appointmentIds.removeIf(id -> id.equals(appointmentId));
        if (removed) {
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "REMOVE_APPOINTMENT",
                "APPOINTMENT",
                appointmentId,
                "Appointment removed with ID: " + appointmentId
            );
        }
        return removed;
    }

    public boolean containsAppointment(String appointmentId) {
        return appointmentIds.contains(appointmentId);
    }

    public int getAppointmentsCount() {
        return appointmentIds.size();
    }

    public List<String> validate() {
        List<String> errors = new ArrayList<>();
        if (companyName == null || companyName.isEmpty()) {
            errors.add("Company name is required");
        }
        if (commercialRegister == null || commercialRegister.isEmpty()) {
            errors.add("Commercial register is required");
        }
        if (getEmail() == null || !getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.add("Invalid email");
        }
        return errors;
    }

    public void printLogs() {
        log.printAllLogs();
    }

    public Log getLog() {
        return log;
    }

    @Override
    public String toString() {
        return "Company{id=" + getId() + ", name='" + companyName + "', properties=" + propertyIds.size() + 
               ", appointments=" + appointmentIds.size() + ", active=" + isActive + "}";
    }
}

	