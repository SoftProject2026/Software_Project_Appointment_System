package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin extends User {
    private List<String> permissions; ///
    private List<String> managedCompanies; ///
    
    public Admin(String id, String name, String username, String email, String password) {
        super(id, name, username, email, password);
        this.permissions = new ArrayList<>(); ///
        this.managedCompanies = new ArrayList<>(); ///
        initializePermissions();
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
    
//////////////////////////////////////////////////////////    
    
    private void initializePermissions() {
        permissions = Arrays.asList(
            "VIEW_ALL_COMPANIES",
            "APPROVE_COMPANY",
            "VIEW_ALL_APPOINTMENTS"
        );
    }
    
    
    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }
    
    
    public boolean canApproveCompany() {
        return hasPermission("APPROVE_COMPANY");
    }
    
    public boolean canViewAllCompanies() {
        return hasPermission("VIEW_ALL_COMPANIES");
    }
    
    public boolean canViewAllAppointments() {
        return hasPermission("VIEW_ALL_APPOINTMENTS");
    }
    
    @Override
    public void logout() {
    
        setActive(false);
    }

    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getName() + "'}";
    }
    
    /*public void addPermission(String permission) {
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
    */
    
   
    /*
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
    */
    
    
}
