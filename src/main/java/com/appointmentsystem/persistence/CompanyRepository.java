package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @author Tala Khraim
 * @author Sara Sawalha
 * @author Masar Jabr
 * 
 * @version 1.0
 */
public class CompanyRepository {
    
	private static Map<String, Company> companyStorage = new HashMap<>();
    
    public void save(Company company) {
        if (company == null) return;
        companyStorage.put(company.getId(), company);
    }
    
    public Company findById(String id) {
        if (id == null) return null;
        return companyStorage.get(id);
    }
    
    public Company findByUsername(String username) {
        if (username == null) return null;
        return companyStorage.values().stream()
                .filter(c -> username.equals(c.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    public Company findByCompanyName(String companyName) {
        if (companyName == null) return null;
        return companyStorage.values().stream()
                .filter(c -> companyName.equalsIgnoreCase(c.getCompanyName()))
                .findFirst()
                .orElse(null);
    }
    
    public List<Company> findAll() {
        return new ArrayList<>(companyStorage.values());
    }
    
    
    public List<Company> findAllVerified() {
        return companyStorage.values().stream()
                .filter(Company::isVerified)
                .collect(Collectors.toList());
    }
    
    public void update(Company company) {
        if (company == null || !companyStorage.containsKey(company.getId())) return;
        companyStorage.put(company.getId(), company);
    }
    
    public void delete(String id) {
        if (id == null) return;
        companyStorage.remove(id);
    }
    
 
    
    public void clear() {
        companyStorage.clear();
    }
}