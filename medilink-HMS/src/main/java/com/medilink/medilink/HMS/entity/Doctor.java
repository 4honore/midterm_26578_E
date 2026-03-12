package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Doctor Entity - Represents doctors working in the hospital system
 * 
 * EXPLANATION:
 * - Doctors are assigned to specific locations (hospitals)
 * - @ManyToOne with Location: Many doctors work at one location
 * - @OneToMany with Appointments: One doctor can have many appointments
 * - @ManyToMany with Patients: Doctors can treat multiple patients
 * - @JoinTable creates an intermediate table for the many-to-many relationship
 * - joinColumns defines the foreign key for Doctor side
 * - inverseJoinColumns defines the foreign key for Patient side
 */
@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String specialization;
    
    @Column(nullable = false)
    private String phone;
    
    // MANY-TO-ONE RELATIONSHIP: Many Doctors work at One Location
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    
    // ONE-TO-MANY RELATIONSHIP: One Doctor can have many Appointments
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;
    
    // MANY-TO-MANY RELATIONSHIP: Doctors can treat multiple Patients
    // @JoinTable creates a join table "doctor_patient" with two foreign keys
    // This is the owning side of the relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "doctor_patient",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    @JsonIgnore
    private List<Patient> patients;
}
