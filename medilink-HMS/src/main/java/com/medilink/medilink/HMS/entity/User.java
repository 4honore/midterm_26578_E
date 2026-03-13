package com.medilink.medilink.HMS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity - Represents system users with different roles
 * 
 * EXPLANATION:
 * - Users can have roles: ADMIN, DOCTOR, NURSE, RECEPTIONIST, PHARMACIST
 * - Each user is assigned to a specific location (hospital)
 * - @ManyToOne relationship means many users can belong to one location
 * - @JoinColumn specifies the foreign key column name in the database
 * - Username must be unique to prevent duplicate accounts
 * - Comprehensive validation ensures data integrity
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    @Column(nullable = false, unique = true)
    private String username;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(ADMIN|DOCTOR|NURSE|RECEPTIONIST|PHARMACIST|TECHNICIAN)$", 
             message = "Role must be one of: ADMIN, DOCTOR, NURSE, RECEPTIONIST, PHARMACIST, TECHNICIAN")
    @Column(nullable = false)
    private String role; // ADMIN, DOCTOR, RECEPTIONIST, etc.
    
    // MANY-TO-ONE RELATIONSHIP: Many Users belong to One Location
    // @JoinColumn creates a foreign key column "location_id" in users table
    // FetchType.EAGER loads location data immediately (needed for JSON serialization)
    @NotNull(message = "Location is required")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    
    // ONE-TO-ONE RELATIONSHIP: One User has exactly One UserProfile
    // CascadeType.ALL means saving a User will save the profile automatically
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfile userProfile;
}
