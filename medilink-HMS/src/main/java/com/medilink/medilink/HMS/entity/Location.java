package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Location Entity - Represents a hierarchical administrative division in a single table
 * 
 * EXPLANATION:
 * - Solves the requirement for ONE table named `locations` that holds the entire hierarchy.
 * - @ManyToOne with itself (`parent_id`) creates a self-referencing hierarchy.
 * - This allows saving Province -> District -> Sector -> Cell -> Village interconnected.
 * - Users are assigned to the Village level, which transitively connects them to the Province.
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
    private String code; // e.g. "KC" for Kigali City, "VIL-01" etc
    
    @Column(nullable = false, length = 100)
    private String name; // e.g. "Kigali City", "Gasabo", "Kigali Central"
    
    @Column(nullable = false, length = 50)
    private String locationType; // "PROVINCE", "DISTRICT", "SECTOR", "CELL", "VILLAGE"
    
    @Column(length = 200)
    private String hospitalName; // Nullable, populated at the Village level
    
    // SELF-REFERENCING RELATIONSHIP (Hierarchical Structure)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore // Prevents infinite recursion
    @ToString.Exclude // Prevent stack overflow in toString
    private Location parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Location> subLocations;
    
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
}
