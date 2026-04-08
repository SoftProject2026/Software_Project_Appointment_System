package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Admin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminRepository {
    
	private Admin admin;
    
    
    public void save(Admin admin) {
        if (admin == null) return;
        this.admin = admin;
    }
    
    public Admin getAdmin() {
        return admin;
    }

    public void update(Admin admin) {
        if (admin == null) return;
        this.admin = admin;
    }
    /*
    public Admin findById(String id) {
        if (id == null) return null;
        return adminStorage.get(id);
    }
    
    public Admin findByUsername(String username) {
        if (username == null) return null;
        return adminStorage.values().stream()
                .filter(admin -> username.equals(admin.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Admin> findAll() {
        return new ArrayList<>(adminStorage.values());
    }
    
    public void update(Admin admin) {
        if (admin == null || !adminStorage.containsKey(admin.getId())) return;
        adminStorage.put(admin.getId(), admin);
    }
    
    public void delete(String id) {
        if (id == null) return;
        adminStorage.remove(id);
    }
    
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return adminStorage.values().stream()
                .anyMatch(admin -> username.equals(admin.getUsername()));
    }
    
    // Permission management (persistence only)
    public void addPermission(String permission) {
        if (permission == null) return;
        if (!permissions.contains(permission))
            permissions.add(permission);
    }
    
    public void removePermission(String permission) {
        if (permission == null) return;
        permissions.remove(permission);
    }
    
    public void addAllPermissions(List<String> permissionList) {
        if (permissionList == null) return;
        permissions.addAll(permissionList);
    }
    
    public List<String> getAllPermissions() {
        return new ArrayList<>(permissions);
    }
    
    
    public void addManagedCompany(String companyId) {
        if (companyId == null) return;
        if (!managedCompanies.contains(companyId))
            managedCompanies.add(companyId);
    }
    
    public void removeManagedCompany(String companyId) {
        if (companyId == null) return;
        managedCompanies.remove(companyId);
    }
    
    public List<String> getAllManagedCompanies() {
        return new ArrayList<>(managedCompanies);
    }*/
}