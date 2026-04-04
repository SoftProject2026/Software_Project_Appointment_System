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
    private int establishedYear;
    private List<Property> properties;
    private List<Appointment> appointments;
    private boolean isActive;
    private boolean isVerified;
    private Log log;

    public Company(String id, String companyName, String username, String email,
                   String password, String commercialRegister) {
        super(id, companyName, username, email, password);
        this.companyName = companyName;
        this.commercialRegister = commercialRegister;
        this.properties = new ArrayList<>();
        this.appointments = new ArrayList<>();
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
        this.establishedYear = establishedYear;
        this.properties = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.log = new Log();
        this.isActive = true;
        this.isVerified = false;
    }

    @Override
    public String getRole() {
        return "COMPANY";
    }

    public String getCompanyName() {
		// TODO Auto-generated method stub
		return companyName;
	}

    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getCommercialRegister() { return commercialRegister; }
    public void setCommercialRegister(String commercialRegister) { this.commercialRegister = commercialRegister; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(int establishedYear) { this.establishedYear = establishedYear; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean verified) { isVerified = verified; }

    public List<Property> getProperties() {
        return new ArrayList<>(properties);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public void addProperty(Property property) {
        if (!properties.contains(property)) {
            properties.add(property);
            property.setCompanyId(this.getId());
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "ADD_PROPERTY",
                "PROPERTY",
                property.getId(),
                "Added new property: " + property.getTitle()
            );
        }
    }

    public boolean removeProperty(String propertyId) {
        Property propertyToRemove = findPropertyById(propertyId);
        boolean removed = properties.removeIf(p -> p.getId().equals(propertyId));
        if (removed && propertyToRemove != null) {
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "REMOVE_PROPERTY",
                "PROPERTY",
                propertyId,
                "Removed property: " + propertyToRemove.getTitle()
            );
        }
        return removed;
    }

    public List<Property> getAllProperties() {
        return new ArrayList<>(properties);
    }

    public List<Property> getAvailableProperties() {
        return properties.stream()
                .filter(p -> p.getStatus() == PropertyStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public int getAvailablePropertiesCount() {
        return (int) properties.stream()
                .filter(p -> p.getStatus() == PropertyStatus.AVAILABLE)
                .count();
    }

    public Property findPropertyById(String propertyId) {
        return properties.stream()
                .filter(p -> p.getId().equals(propertyId))
                .findFirst()
                .orElse(null);
    }

    public int getPropertiesCount() {
        return properties.size();
    }

    public int getSoldPropertiesCount() {
        return (int) properties.stream()
                .filter(p -> p.getStatus() == PropertyStatus.SOLD)
                .count();
    }

    public void addAppointment(Appointment appointment) {
        if (!appointments.contains(appointment)) {
            appointments.add(appointment);
            log.log(
                String.valueOf(getId()),
                "COMPANY",
                "ADD_APPOINTMENT",
                "APPOINTMENT",
                appointment.getId(),
                "New appointment scheduled"
            );
        }
    }

    public boolean cancelAppointment(String appointmentId) {
        for (Appointment app : appointments) {
            if (app.getId().equals(appointmentId)) {
                app.setStatus(AppointmentStatus.CANCELLED);
                log.log(
                    String.valueOf(getId()),
                    "COMPANY",
                    "CANCEL_APPOINTMENT",
                    "APPOINTMENT",
                    appointmentId,
                    "Appointment cancelled"
                );
                return true;
            }
        }
        return false;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<Appointment> getTodayAppointments() {
        return appointments.stream()
                .filter(Appointment::isToday)
                .collect(Collectors.toList());
    }

    public int getTodayAppointmentsCount() {
        return (int) appointments.stream()
                .filter(Appointment::isToday)
                .count();
    }

    public List<Appointment> getUpcomingAppointments() {
        return appointments.stream()
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED && a.isInFuture())
                .collect(Collectors.toList());
    }

    public CompanyStatistics getStatistics() {
        return new CompanyStatistics(
                properties.size(),
                getAvailablePropertiesCount(),
                getSoldPropertiesCount(),
                appointments.size(),
                getUpcomingAppointments().size(),
                (int) appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.COMPLETED).count(),
                getTodayAppointmentsCount()
        );
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
        return "Company{id=" + getId() + ", name='" + companyName + "', properties=" + properties.size() + ", appointments=" + appointments.size() + ", active=" + isActive + "}";
    }

	
	}

	