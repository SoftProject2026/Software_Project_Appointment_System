
/*
package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private List<User> users;
    private List<Company> companies;
    private List<Appointment> allAppointments;
    private Log systemLog;
    
    public SystemManager() {
        this.users = new ArrayList<>();
        this.companies = new ArrayList<>();
        this.allAppointments = new ArrayList<>();
        this.systemLog = new Log();
    }
    
    public void addUser(User user) {
        users.add(user);
        systemLog.log(user.getId(), user.getRole(), "ADD_USER", "USER", user.getId(), "User added");
    }
    
    public void removeUser(String userId) {
        users.removeIf(u -> u.getId().equals(userId));
        systemLog.log("SYSTEM", "SYSTEM", "REMOVE_USER", "USER", userId, "User removed");
    }
    
    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
    
    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
    public List<User> getUsersByRole(String role) {
        List<User> result = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(role)) {
                result.add(user);
            }
        }
        return result;
    }
    
    public void addCompany(Company company) {
        companies.add(company);
        systemLog.log(company.getId(), "COMPANY", "ADD_COMPANY", "COMPANY", company.getId(), "Company added");
    }
    
    public void removeCompany(String companyId) {
        companies.removeIf(c -> c.getId().equals(companyId));
        systemLog.log("SYSTEM", "SYSTEM", "REMOVE_COMPANY", "COMPANY", companyId, "Company removed");
    }
    
    public Company findCompanyById(String companyId) {
        for (Company company : companies) {
            if (company.getId().equals(companyId)) {
                return company;
            }
        }
        return null;
    }
    
    public List<Company> getAllCompanies() {
        return new ArrayList<>(companies);
    }
    
    public void addAppointment(Appointment appointment) {
        allAppointments.add(appointment);
        systemLog.log(appointment.getVisitorId(), "VISITOR", "ADD_APPOINTMENT", "APPOINTMENT", appointment.getId(), "Appointment created");
    }
    
    public void cancelAppointment(String appointmentId) {
        for (Appointment app : allAppointments) {
            if (app.getId().equals(appointmentId)) {
                app.cancel();
                systemLog.log(app.getVisitorId(), "VISITOR", "CANCEL_APPOINTMENT", "APPOINTMENT", appointmentId, "Appointment cancelled");
                break;
            }
        }
    }
    
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(allAppointments);
    }
    
    public List<Appointment> getAppointmentsByCompany(String companyId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : allAppointments) {
            if (app.getCompanyId().equals(companyId)) {
                result.add(app);
            }
        }
        return result;
    }
    
    public List<Appointment> getAppointmentsByVisitor(String visitorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : allAppointments) {
            if (app.getVisitorId().equals(visitorId)) {
                result.add(app);
            }
        }
        return result;
    }
    
    public Log getSystemLog() {
        return systemLog;
    }
    
    public void printSystemLogs() {
        systemLog.printAllLogs();
    }
    
    public int getTotalUsers() {
        return users.size();
    }
    
    public int getTotalCompanies() {
        return companies.size();
    }
    
    public int getTotalAppointments() {
        return allAppointments.size();
    }
    
    @Override
    public String toString() {
        return "SystemManager{users=" + users.size() + ", companies=" + companies.size() + ", appointments=" + allAppointments.size() + "}";
    }
}*/