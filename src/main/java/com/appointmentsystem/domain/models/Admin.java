package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends User {
    private String adminLevel;
    private List<String> permissions;
    private List<String> managedCompanies;
    
    public Admin(String id, String name, String username, String email, String password) {
        super(id, name, username, email, password);
        this.adminLevel = "NORMAL";
        this.permissions = new ArrayList<>();
        this.managedCompanies = new ArrayList<>();
        initializePermissions();
    }
    
    public Admin(String id, String name, String username, String email, String password, String adminLevel) {
        super(id, name, username, email, password);
        this.adminLevel = adminLevel;
        this.permissions = new ArrayList<>();
        this.managedCompanies = new ArrayList<>();
        initializePermissions();
    }
    
    private void initializePermissions() {
        if ("SUPER".equals(adminLevel)) {
            permissions.addAll(Arrays.asList(
                "ADD_COMPANY",
                "REMOVE_COMPANY",
                "VIEW_ALL_COMPANIES",
                "VIEW_ALL_APPOINTMENTS",
                "DEACTIVATE_COMPANY",
                "ACTIVATE_COMPANY",
                "GENERATE_REPORTS",
                "VIEW_LOGS",
                "MANAGE_ADMINS",
                "SYSTEM_CONFIG"
            ));
        } else {
            permissions.addAll(Arrays.asList(
                "VIEW_ALL_COMPANIES",
                "VIEW_ALL_APPOINTMENTS",
                "GENERATE_REPORTS"
            ));
        }
    }
    
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
        initializePermissions();
    }
    
    public boolean isSuperAdmin() {
        return "SUPER".equals(adminLevel);
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public void addPermission(String permission) {
        if (!permissions.contains(permission)) {
            permissions.add(permission);
        }
    }
    
    public void removePermission(String permission) {
        permissions.remove(permission);
    }
    
    public List<String> getPermissions() {
        return new ArrayList<>(permissions);
    }
    
    public List<String> getManagedCompanies() {
        return new ArrayList<>(managedCompanies);
    }
    
    public void addManagedCompany(String companyId) {
        if (!managedCompanies.contains(companyId)) {
            managedCompanies.add(companyId);
        }
    }
    
    public void removeManagedCompany(String companyId) {
        managedCompanies.remove(companyId);
    }
    
    public boolean managesCompany(String companyId) {
        return managedCompanies.contains(companyId);
    }
    
    public int getManagedCompaniesCount() {
        return managedCompanies.size();
    }
    
    public boolean hasManagedCompanies() {
        return !managedCompanies.isEmpty();
    }
    
    public boolean canAddCompany() {
        return hasPermission("ADD_COMPANY");
    }
    
    public boolean canRemoveCompany() {
        return hasPermission("REMOVE_COMPANY");
    }
    
    public boolean canViewAllCompanies() {
        return hasPermission("VIEW_ALL_COMPANIES");
    }
    
    public boolean canViewAllAppointments() {
        return hasPermission("VIEW_ALL_APPOINTMENTS");
    }
    
    public boolean canDeactivateCompany() {
        return hasPermission("DEACTIVATE_COMPANY");
    }
    
    public boolean canActivateCompany() {
        return hasPermission("ACTIVATE_COMPANY");
    }
    
    public boolean canGenerateReports() {
        return hasPermission("GENERATE_REPORTS");
    }
    
    public boolean canViewLogs() {
        return hasPermission("VIEW_LOGS");
    }
    
    public boolean canManageAdmins() {
        return hasPermission("MANAGE_ADMINS");
    }
    
    public boolean canConfigureSystem() {
        return hasPermission("SYSTEM_CONFIG");
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
    
    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getName() + "', username='" + getUsername() + "', level='" + adminLevel + "', permissions=" + permissions.size() + ", managedCompanies=" + managedCompanies.size() + "}";
    }
}

/*
package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.appointmentsystem.domain.models.Company;
import com.appointmentsystem.persistence.AdminRepository;

public class Admin extends User {
    private String adminLevel;
    private List<String> permissions;
    private List<String> managedCompanies;
    
    private AdminRepository AdminRepo=new AdminRepository();
    
    public Admin(String id, String name, String username, String email, String password) {
        super(id, name, username, email, password);
        this.adminLevel = "NORMAL";
        this.permissions = new ArrayList<>();
        this.managedCompanies = new ArrayList<>();
        initializePermissions();
    }
    
    public Admin(String id, String name, String username, String email, String password, String adminLevel) {
        super(id, name, username, email, password);
        this.adminLevel = adminLevel;
        this.permissions = new ArrayList<>();
        this.managedCompanies = new ArrayList<>();
        initializePermissions();
    }
    
    private void initializePermissions() {
        if ("SUPER".equals(adminLevel)) {
            permissions.addAll(Arrays.asList(
                "ADD_COMPANY",
                "REMOVE_COMPANY",
                "VIEW_ALL_COMPANIES",
                "VIEW_ALL_APPOINTMENTS",
                "DEACTIVATE_COMPANY",
                "ACTIVATE_COMPANY",
                "GENERATE_REPORTS",
                "VIEW_LOGS",
                "MANAGE_ADMINS",
                "SYSTEM_CONFIG"
            ));
        } else {
            permissions.addAll(Arrays.asList(
                "VIEW_ALL_COMPANIES",
                "VIEW_ALL_APPOINTMENTS",
                "GENERATE_REPORTS"
            ));
        }
    }
    
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
        initializePermissions();
    }
    
    public boolean isSuperAdmin() {
        return "SUPER".equals(adminLevel);
    }
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    public void addPermission(String permission) {
        if (!permissions.contains(permission)) {
            permissions.add(permission);
        }
    }
    
    public void removePermission(String permission) {
        permissions.remove(permission);
    }
    
    public List<String> getPermissions() {
        return new ArrayList<>(permissions);
    }
    
    public List<String> getManagedCompanies() {
        return new ArrayList<>(managedCompanies);
    }
    
    public void addManagedCompany(String companyId) {
        if (!managedCompanies.contains(companyId)) {
            managedCompanies.add(companyId);
        }
    }
    
    public void removeManagedCompany(String companyId) {
        managedCompanies.remove(companyId);
    }
    
    public boolean managesCompany(String companyId) {
        return managedCompanies.contains(companyId);
    }
    
    public List<Company> getManagedCompaniesByName(String companyName, List<Company> allCompanies) {
        List<Company> result = new ArrayList<>();
        if (companyName == null) return result;
        for (Company company : allCompanies) {
            String id = String.valueOf(company.getId());
            String name = company.getCompanyName() != null ? company.getCompanyName() : "";
            if (managedCompanies.contains(id) && name.toLowerCase().contains(companyName.toLowerCase())) {
                result.add(company);
            }
        }
        return result;
    }

    public Company findManagedCompanyByName(String companyName, List<Company> allCompanies) {
        if (companyName == null) return null;
        for (Company company : allCompanies) {
            String id = String.valueOf(company.getId());
            String name = company.getCompanyName() != null ? company.getCompanyName() : "";
            if (managedCompanies.contains(id) && name.equalsIgnoreCase(companyName)) {
                return company;
            }
        }
        return null;
    }

    public List<Company> searchManagedCompaniesByName(String searchTerm, List<Company> allCompanies) {
        List<Company> result = new ArrayList<>();
        if (searchTerm == null) return result;
        for (Company company : allCompanies) {
            String id = String.valueOf(company.getId());
            String name = company.getCompanyName() != null ? company.getCompanyName() : "";
            if (managedCompanies.contains(id) && name.toLowerCase().contains(searchTerm.toLowerCase())) {
                result.add(company);
            }
        }
        return result;
    }
    
    public int getManagedCompaniesCount() {
        return managedCompanies.size();
    }
    
    public boolean hasManagedCompanies() {
        return !managedCompanies.isEmpty();
    }
    
    public boolean canAddCompany() {
        return hasPermission("ADD_COMPANY");
    }
    
    public boolean canRemoveCompany() {
        return hasPermission("REMOVE_COMPANY");
    }
    
    public boolean canViewAllCompanies() {
        return hasPermission("VIEW_ALL_COMPANIES");
    }
    
    public boolean canViewAllAppointments() {
        return hasPermission("VIEW_ALL_APPOINTMENTS");
    }
    
    public boolean canDeactivateCompany() {
        return hasPermission("DEACTIVATE_COMPANY");
    }
    
    public boolean canActivateCompany() {
        return hasPermission("ACTIVATE_COMPANY");
    }
    
    public boolean canGenerateReports() {
        return hasPermission("GENERATE_REPORTS");
    }
    
    public boolean canViewLogs() {
        return hasPermission("VIEW_LOGS");
    }
    
    public boolean canManageAdmins() {
        return hasPermission("MANAGE_ADMINS");
    }
    
    public boolean canConfigureSystem() {
        return hasPermission("SYSTEM_CONFIG");
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
    
    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getName() + "', username='" + getUsername() + "', level='" + adminLevel + "', permissions=" + permissions.size() + ", managedCompanies=" + managedCompanies.size() + "}";
    }
}*/