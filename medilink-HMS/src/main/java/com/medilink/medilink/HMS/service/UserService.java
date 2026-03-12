package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.User;
import com.medilink.medilink.HMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────


// ─── Implementation ──────────────────────────────────────────────────────────

/**
 * UserServiceImpl - Business logic for User operations
 *
 * EXPLANATION OF QUERY METHODS:
 *
 * 1. getUsersByProvinceName():
 *    - Retrieves all users working in a specific province by name
 *    - Uses repository method findByLocation_ProvinceName()
 *    - Example: Get all users in "Kigali City"
 *
 * 2. getUsersByProvinceCode():
 *    - Retrieves all users working in a specific province by code
 *    - Uses repository method findByLocation_ProvinceCode()
 *    - Example: Get all users in province with code "KC"
 *
 * These methods demonstrate the exam requirement:
 * "Retrieve all users from a given province using province code OR province name"
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
public User saveUser(User user) {
        // EXAM REQUIREMENT: Validate duplicate username before saving
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("User with username " + user.getUsername() + " already exists");
        }
        return userRepository.save(user);
    }
public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
public List<User> getAllUsers() {
        return userRepository.findAll();
    }
public Page<User> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
public List<User> getUsersByProvinceName(String provinceName) {
        // EXAM REQUIREMENT: Retrieve users by province name
        // This method queries users based on their location's province name by traversing the hierarchy up to the province level (4 parents up)
        return userRepository.findUsersByProvinceName(provinceName);
    }
public List<User> getUsersByProvinceCode(String provinceCode) {
        // EXAM REQUIREMENT: Retrieve users by province code
        // This method queries users based on their location's province code
        return userRepository.findUsersByProvinceCode(provinceCode);
    }
public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        existingUser.setLocation(user.getLocation());
        return userRepository.save(existingUser);
    }
public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}


