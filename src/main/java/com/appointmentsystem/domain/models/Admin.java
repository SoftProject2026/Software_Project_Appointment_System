package com.appointmentsystem.domain.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admin {
    private String id;
    private String username;
    private String password;

    public Admin(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    

    @Override
    public String toString() {
        return "Admin{id='" + getId() + "', name='" + getUsername() + "'}";
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
