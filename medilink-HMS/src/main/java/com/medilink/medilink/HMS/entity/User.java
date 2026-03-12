package com.medilink.medilink.HMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Entity - Represents system users with different roles
 * 
 * EXPLANATION:
 * - Users can have roles: ADMIN, DOCTOR, or RECEPTIONIST
 * - Each user is assigned to a specific location (hospital)
 * - @ManyToOne relationship means many users can belong to one location
 * - @JoinColumn specifies the foreign key column name in the database
 * - Username must be unique to prevent duplicate accounts
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
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role; // ADMIN, DOCTOR, RECEPTIONIST
    
    // MANY-TO-ONE RELATIONSHIP: Many Users belong to One Location
    // @JoinColumn creates a foreign key column "location_id" in users table
    // FetchType.EAGER loads location data immediately (needed for JSON serialization)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    
    // ONE-TO-ONE RELATIONSHIP: One User has exactly One UserProfile
    // CascadeType.ALL means saving a User will save the profile automatically
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private UserProfile userProfile;
}
