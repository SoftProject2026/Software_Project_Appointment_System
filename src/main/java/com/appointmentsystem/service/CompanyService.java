package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.domain.models.Property;
import com.appointmentsystem.domain.models.Appointment;
import com.appointmentsystem.domain.models.CompanyStatistics;
import com.appointmentsystem.domain.models.enums.AppointmentStatus;
import com.appointmentsystem.domain.models.enums.PropertyStatus;
import com.appointmentsystem.persistence.CompanyRepository;
import com.appointmentsystem.persistence.PropertyRepository;
import com.appointmentsystem.persistence.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    
    private CompanyRepository companyRepository;
    private PropertyRepository propertyRepository;
    private AppointmentRepository appointmentRepository;
    
    public CompanyService() {
        this.companyRepository = new CompanyRepository();
        this.propertyRepository = new PropertyRepository();
        this.appointmentRepository = new AppointmentRepository();
    }
    
    public CompanyService(CompanyRepository companyRepository, 
                          PropertyRepository propertyRepository,
                          AppointmentRepository appointmentRepository) {
        this.companyRepository = companyRepository;
        this.propertyRepository = propertyRepository;
        this.appointmentRepository = appointmentRepository;
    }
    
    
    public void createCompany(Company company) {
        if (company == null) return;
        companyRepository.save(company);
    }
    
    public Company getCompanyById(String id) {
        return companyRepository.findById(id);
    }
    
    public Company getCompanyByUsername(String username) {
        return companyRepository.findByUsername(username);
    }
    
    public Company getCompanyByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
    
    public List<Company> getAllActiveCompanies() {
        return companyRepository.findAllActive();
    }
    
    public void updateCompany(Company company) {
        companyRepository.update(company);
    }
    
    public void deleteCompany(String id) {
        companyRepository.delete(id);
    }
    
    
    
    public void addPropertyToCompany(String companyId, Property property) {
        Company company = companyRepository.findById(companyId);
        if (company == null || property == null) return;
        
        property.setCompanyId(companyId);
        propertyRepository.save(property);
        company.addPropertyId(property.getId());
        companyRepository.update(company);
    }
    
    public void removePropertyFromCompany(String companyId, String propertyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) return;
        
        company.removePropertyId(propertyId);
        propertyRepository.delete(propertyId);
        companyRepository.update(company);
    }
    
    public List<Property> getCompanyProperties(String companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) return new ArrayList<>();
        
        return company.getPropertyIds().stream()
                .map(propertyRepository::findById)
                .filter(p -> p != null)
                .collect(Collectors.toList());
    }
    
    public List<Property> getAvailableProperties(String companyId) {
        return getCompanyProperties(companyId).stream()
                .filter(p -> p.getStatus() == PropertyStatus.AVAILABLE)
                .collect(Collectors.toList());
    }
    
    public List<Property> getAvailableProperties(String companyId, double minPrice, double maxPrice) {
        return getAvailableProperties(companyId).stream()
                .filter(p -> p.getPrice() >= minPrice && p.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    
    public Property findPropertyById(String propertyId) {
        return propertyRepository.findById(propertyId);
    }
    
    public int getPropertiesCount(String companyId) {
        Company company = companyRepository.findById(companyId);
        return company != null ? company.getPropertiesCount() : 0;
    }
    
    public int getAvailablePropertiesCount(String companyId) {
        return getAvailableProperties(companyId).size();
    }
    
    public int getSoldPropertiesCount(String companyId) {
        return (int) getCompanyProperties(companyId).stream()
                .filter(p -> p.getStatus() == PropertyStatus.SOLD)
                .count();
    }
    
    
    
    public void addAppointmentToCompany(String companyId, Appointment appointment) {
        Company company = companyRepository.findById(companyId);
        if (company == null || appointment == null) return;
        
        appointmentRepository.save(appointment);
        company.addAppointmentId(appointment.getId());
        companyRepository.update(company);
    }
    
    public void cancelAppointment(String companyId, String appointmentId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) return;
        
        Appointment appointment = appointmentRepository.findById(appointmentId);
        if (appointment != null) {
            appointment.cancel();
            appointmentRepository.update(appointment);
        }
    }
    
    public List<Appointment> getCompanyAppointments(String companyId) {
        Company company = companyRepository.findById(companyId);
        if (company == null) return new ArrayList<>();
        
        return company.getAppointmentIds().stream()
                .map(appointmentRepository::findById)
                .filter(a -> a != null)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getTodayAppointments(String companyId) {
        return getCompanyAppointments(companyId).stream()
                .filter(Appointment::isToday)
                .collect(Collectors.toList());
    }
    
    public List<Appointment> getUpcomingAppointments(String companyId) {
        return getCompanyAppointments(companyId).stream()
                .filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED && a.isInFuture())
                .collect(Collectors.toList());
    }
    
    public int getTodayAppointmentsCount(String companyId) {
        return getTodayAppointments(companyId).size();
    }
    
    public int getUpcomingAppointmentsCount(String companyId) {
        return getUpcomingAppointments(companyId).size();
    }
    
    // ========== Statistics ==========
    
    public CompanyStatistics getCompanyStatistics(String companyId) {
        List<Property> properties = getCompanyProperties(companyId);
        List<Appointment> appointments = getCompanyAppointments(companyId);
        
        return new CompanyStatistics(
                properties.size(),
                (int) properties.stream().filter(p -> p.getStatus() == PropertyStatus.AVAILABLE).count(),
                (int) properties.stream().filter(p -> p.getStatus() == PropertyStatus.SOLD).count(),
                appointments.size(),
                (int) appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.SCHEDULED && a.isInFuture()).count(),
                (int) appointments.stream().filter(a -> a.getStatus() == AppointmentStatus.COMPLETED).count(),
                (int) appointments.stream().filter(Appointment::isToday).count()
        );
    }
    
    
    
    public List<String> validateCompany(Company company) {
        return company != null ? company.validate() : List.of("Company is null");
    }
    
    public boolean isCompanyActive(String companyId) {
        Company company = companyRepository.findById(companyId);
        return company != null && company.isActive();
    }
    
    public void activateCompany(String companyId) {
        Company company = companyRepository.findById(companyId);
        if (company != null) {
            company.setActive(true);
            companyRepository.update(company);
        }
    }
    
    public void deactivateCompany(String companyId) {
        Company company = companyRepository.findById(companyId);
        if (company != null) {
            company.setActive(false);
            companyRepository.update(company);
        }
    }
}