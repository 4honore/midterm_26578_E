package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Location Entity - Hierarchical Administrative Structure (Adjacency List Model)
 * 
 * EXPLANATION:
 * - Single table stores ALL location levels (Province, District, Sector, Cell, Village)
 * - Self-referencing relationship: each location has a parent (except Province which is root)
 * - This creates a tree structure: Province → District → Sector → Cell → Village
 * - Follows the Adjacency List Model for hierarchical data
 * - Users are linked to Village level, but can navigate up to Province through parent references
 */
@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50)
    private String code; // e.g. "KC" for Kigali City Province
    
    @Column(nullable = false, length = 100)
    private String name; // e.g. "Kigali City", "Gasabo", "Kimironko"
    
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private LocationType locationType; // PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE
    
    @Column(length = 200)
    private String hospitalName; // Only populated at Village level
    
    // SELF-REFERENCING RELATIONSHIP (Hierarchical Structure)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore // Prevents infinite recursion
    @ToString.Exclude // Prevent stack overflow in toString
    private Location parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Location> children;
    
    // ONE-TO-MANY RELATIONSHIP: One Location (Village) can have many Users
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;
    
    // ONE-TO-MANY RELATIONSHIP: One Location can have many Doctors
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Doctor> doctors;
    
    // ONE-TO-MANY RELATIONSHIP: One Location can have many Appointments
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Appointment> appointments;
    
    /**
     * Enum for Location Type - ensures only valid location levels exist
     */
    public enum LocationType {
        PROVINCE,
        DISTRICT,
        SECTOR,
        CELL,
        VILLAGE
    }
}
