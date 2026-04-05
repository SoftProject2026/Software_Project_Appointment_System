package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.Admin;
import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.persistence.AdminRepository;
import com.appointmentsystem.persistence.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminService {
    
    private AdminRepository adminRepository;
    private CompanyRepository companyRepository;
    
    public AdminService() {
        this.adminRepository = new AdminRepository();
        this.companyRepository = new CompanyRepository();
    }
    
    public AdminService(AdminRepository adminRepository, CompanyRepository companyRepository) {
        this.adminRepository = adminRepository;
        this.companyRepository = companyRepository;
    }
    
    // ========== Admin CRUD ==========
    public void createAdmin(Admin admin) {
        if (admin == null) return;
        adminRepository.save(admin);
    }
    
    public Admin getAdminById(String id) {
        return adminRepository.findById(id);
    }
    
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public void updateAdmin(Admin admin) {
        adminRepository.update(admin);
    }
    
    public void deleteAdmin(String id) {
        adminRepository.delete(id);
    }
    
    // ========== Business Logic for Admin ==========
    
    public List<Company> getManagedCompanies(Admin admin, List<Company> allCompanies) {
        if (admin == null || allCompanies == null) return new ArrayList<>();
        List<String> managedIds = admin.getManagedCompanies();
        return allCompanies.stream()
                .filter(c -> managedIds.contains(c.getId()))
                .collect(Collectors.toList());
    }
    
    public List<Company> getManagedCompaniesByName(Admin admin, String companyName, List<Company> allCompanies) {
        if (admin == null || companyName == null || allCompanies == null) return new ArrayList<>();
        List<String> managedIds = admin.getManagedCompanies();
        return allCompanies.stream()
                .filter(c -> managedIds.contains(c.getId()))
                .filter(c -> c.getCompanyName() != null && 
                            c.getCompanyName().toLowerCase().contains(companyName.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public Company findManagedCompanyByName(Admin admin, String companyName, List<Company> allCompanies) {
        if (admin == null || companyName == null || allCompanies == null) return null;
        List<String> managedIds = admin.getManagedCompanies();
        return allCompanies.stream()
                .filter(c -> managedIds.contains(c.getId()))
                .filter(c -> c.getCompanyName() != null && 
                            c.getCompanyName().equalsIgnoreCase(companyName))
                .findFirst()
                .orElse(null);
    }
    
    public List<Company> searchManagedCompaniesByName(Admin admin, String searchTerm, List<Company> allCompanies) {
        if (admin == null || searchTerm == null || allCompanies == null) return new ArrayList<>();
        List<String> managedIds = admin.getManagedCompanies();
        return allCompanies.stream()
                .filter(c -> managedIds.contains(c.getId()))
                .filter(c -> c.getCompanyName() != null && 
                            c.getCompanyName().toLowerCase().contains(searchTerm.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public void addManagedCompany(Admin admin, String companyId) {
        if (admin == null || companyId == null) return;
        admin.addManagedCompany(companyId);
        adminRepository.update(admin);
    }
    
    public void removeManagedCompany(Admin admin, String companyId) {
        if (admin == null || companyId == null) return;
        admin.removeManagedCompany(companyId);
        adminRepository.update(admin);
    }
    
    public boolean canAddCompany(Admin admin) {
        return admin != null && admin.canAddCompany();
    }
    
    public boolean canRemoveCompany(Admin admin) {
        return admin != null && admin.canRemoveCompany();
    }
    
    public boolean canViewAllCompanies(Admin admin) {
        return admin != null && admin.canViewAllCompanies();
    }
    
    public boolean canManageAdmins(Admin admin) {
        return admin != null && admin.canManageAdmins();
    }
}