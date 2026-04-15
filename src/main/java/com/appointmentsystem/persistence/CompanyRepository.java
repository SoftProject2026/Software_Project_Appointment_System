
package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Repository for Company CRUD operations.
 * 
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * @version 1.0
 */
public class CompanyRepository {
    
    private static Map<String, Company> companyStorage = new HashMap<>();
    
    /**
     * @param company the company to save
     */
    public void save(Company company) {
        if (company == null) return;
        companyStorage.put(company.getId(), company);
    }
    
    /**
     * @param id the company ID
     * @return the company if found
     */
    public Company findById(String id) {
        if (id == null) return null;
        return companyStorage.get(id);
    }
    
    /**
     * @param username the username
     * @return the company if found
     */
    public Company findByUsername(String username) {
        if (username == null) return null;
        return companyStorage.values().stream()
                .filter(c -> username.equals(c.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * @param companyName the company name
     * @return the company if found
     */
    public Company findByCompanyName(String companyName) {
        if (companyName == null) return null;
        return companyStorage.values().stream()
                .filter(c -> companyName.equalsIgnoreCase(c.getCompanyName()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * @return all companies
     */
    public List<Company> findAll() {
        return new ArrayList<>(companyStorage.values());
    }
    
    /**
     * @return all verified companies
     */
    public List<Company> findAllVerified() {
        return companyStorage.values().stream()
                .filter(Company::isVerified)
                .collect(Collectors.toList());
    }
    
    /**
     * @param company the company to update
     */
    public void update(Company company) {
        if (company == null || !companyStorage.containsKey(company.getId())) return;
        companyStorage.put(company.getId(), company);
    }
    
    /**
     * @param id the company ID to delete
     */
    public void delete(String id) {
        if (id == null) return;
        companyStorage.remove(id);
    }
    

    
    /** Clears all companies (for testing). */
    public void clear() {
        companyStorage.clear();
    }
}