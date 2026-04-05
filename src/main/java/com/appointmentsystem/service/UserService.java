package com.appointmentsystem.service;

import com.appointmentsystem.domain.models.User;
import com.appointmentsystem.persistence.UserRepository;

import java.util.List;

public class UserService {
    
    private UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    
    public void createUser(User user) {
        if (user == null) return;
        userRepository.save(user);
    }
    
    public User getUserById(String id) {
        return userRepository.findById(id);
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }
    
    public void updateUser(User user) {
        userRepository.update(user);
    }
    
    public void deleteUser(String id) {
        userRepository.delete(id);
    }
    
    
    
    public boolean authenticate(String username, String password) {
        User user = getUserByUsername(username);
        return user != null && user.checkPassword(password);
    }
    
    public void login(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.login();
            userRepository.update(user);
        }
    }
    
    public void logout(String username) {
        User user = getUserByUsername(username);
        if (user != null) {
            user.logout();
            userRepository.update(user);
        }
    }
    
    
    
    public void activateUser(String userId) {
        User user = getUserById(userId);
        if (user != null) {
            user.setActive(true);
            userRepository.update(user);
        }
    }
    
    public void deactivateUser(String userId) {
        User user = getUserById(userId);
        if (user != null) {
            user.setActive(false);
            userRepository.update(user);
        }
    }
    
    public boolean isUserActive(String userId) {
        User user = getUserById(userId);
        return user != null && user.isActive();
    }
    
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public void changePassword(String userId, String newPassword) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userRepository.update(user);
        }
    }
    
    
    
    public int getTotalUsersCount() {
        return userRepository.count();
    }
    
    public int getUsersCountByRole(String role) {
        return getUsersByRole(role).size();
    }
    
    public int getActiveUsersCount() {
        return getAllActiveUsers().size();
    }
}