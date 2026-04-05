package com.appointmentsystem.persistence;

import com.appointmentsystem.domain.models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {
    
    private Map<String, User> userStorage = new HashMap<>();
    
    
    public void save(User user) {
        if (user == null) return;
        userStorage.put(user.getId(), user);
    }
    
    public User findById(String id) {
        if (id == null) return null;
        return userStorage.get(id);
    }
    
    public User findByUsername(String username) {
        if (username == null) return null;
        return userStorage.values().stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElse(null);
    }
    
    public List<User> findAll() {
        return new ArrayList<>(userStorage.values());
    }
    
    public List<User> findByRole(String role) {
        if (role == null) return new ArrayList<>();
        return userStorage.values().stream()
                .filter(u -> role.equals(u.getRole()))
                .collect(Collectors.toList());
    }
    
    public List<User> findAllActive() {
        return userStorage.values().stream()
                .filter(User::isActive)
                .collect(Collectors.toList());
    }
    
    public void update(User user) {
        if (user == null || !userStorage.containsKey(user.getId())) return;
        userStorage.put(user.getId(), user);
    }
    
    public void delete(String id) {
        if (id == null) return;
        userStorage.remove(id);
    }
    
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return userStorage.values().stream()
                .anyMatch(u -> username.equals(u.getUsername()));
    }
    
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return userStorage.values().stream()
                .anyMatch(u -> email.equals(u.getEmail()));
    }
    
    public int count() {
        return userStorage.size();
    }
    
    public void clear() {
        userStorage.clear();
    }
}