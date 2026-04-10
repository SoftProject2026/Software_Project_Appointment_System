package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.persistence.CompanyRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    
	private CompanyRepository companyRepository = new CompanyRepository();
//    private PropertyRepository propertyRepository;
//    private AppointmentRepository appointmentRepository;
//    
//    public CompanyService() {
//        this.companyRepository = new CompanyRepository();
//        this.propertyRepository = new PropertyRepository();
//        this.appointmentRepository = new AppointmentRepository();
//    }
//    
	
    public void signup(Company c) {
        if (companyRepository.findByUsername(c.getUsername()) != null)
            throw new RuntimeException("Username exists");
        if(isValidEmail(c.getEmail()))
        	companyRepository.save(c);
    }

    public Company login(String username, String password) {
        Company c = companyRepository.findByUsername(username);

        if (c == null || !c.getPassword().equals(password))
            throw new RuntimeException("Invalid credentials");

        if (!c.isVerified())
            throw new RuntimeException("Company not approved");

        return c;
    }
	
    public void approve(String username) {
        Company c = companyRepository.findByUsername(username);

        if (c == null)
            throw new RuntimeException("Not found");

        if (c.isVerified())
            throw new RuntimeException("Already approved");

        c.setVerified(true);
        companyRepository.update(c);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
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
    
    /////////////////////////////////////////////
//    public Company getCompanyById(String id) {
//        return companyRepository.findById(id);
//    }
//    
//    public Company getCompanyByUsername(String username) {
//        return companyRepository.findByUsername(username);
//    }
//    
//    public Company getCompanyByCompanyName(String companyName) {
//        return companyRepository.findByCompanyName(companyName);
//    }
//    
//    public List<Company> getAllCompanies() {
//        return companyRepository.findAll();
//    }
//    
//    
//    public void updateCompany(Company company) {
//        companyRepository.update(company);
//    }
//    
//    public void deleteCompany(String id) {
//        companyRepository.delete(id);
//    }
//    
//    
//    
//    public void addPropertyToCompany(String companyId, Property property) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null || property == null) return;
//        
//        property.setCompanyId(companyId);
//        propertyRepository.save(property);
//        company.addPropertyId(property.getId());
//        companyRepository.update(company);
//    }
//    
//    public void removePropertyFromCompany(String companyId, String propertyId) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null) return;
//        
//        company.removePropertyId(propertyId);
//        propertyRepository.delete(propertyId);
//        companyRepository.update(company);
//    }
//    
//    public List<Property> getCompanyProperties(String companyId) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null) return new ArrayList<>();
//        
//        return company.getPropertyIds().stream()
//                .map(propertyRepository::findById)
//                .filter(p -> p != null)
//                .collect(Collectors.toList());
//    }
//    
//    
//    public Property findPropertyById(String propertyId) {
//        return propertyRepository.findById(propertyId);
//    }
//    
//    public int getPropertiesCount(String companyId) {
//        Company company = companyRepository.findById(companyId);
//        return company != null ? company.getPropertiesCount() : 0;
//    }
//    
//
//    
//    public void addAppointmentToCompany(String companyId, Appointment appointment) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null || appointment == null) return;
//        
//        appointmentRepository.save(appointment);
//        company.addAppointmentId(appointment.getId());
//        companyRepository.update(company);
//    }
//    
//    public void cancelAppointment(String companyId, String appointmentId) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null) return;
//        
//        Appointment appointment = appointmentRepository.findById(appointmentId);
//        if (appointment != null) {
//            appointment.cancel();
//            appointmentRepository.update(appointment);
//        }
//    }
//    
//    public List<Appointment> getCompanyAppointments(String companyId) {
//        Company company = companyRepository.findById(companyId);
//        if (company == null) return new ArrayList<>();
//        
//        return company.getAppointmentIds().stream()
//                .map(appointmentRepository::findById)
//                .filter(a -> a != null)
//                .collect(Collectors.toList());
//    }
//    
//    public List<String> validateCompany(Company company) {
//        return company != null ? company.validate() : List.of("Company is null");
//    }
    

}