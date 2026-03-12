package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Patient Entity - Represents patients in the hospital system
 * 
 * EXPLANATION:
 * - Patients are identified by unique National ID (Digital ID simulation)
 * - @OneToMany relationships with Appointments and MedicalRecords
 * - @ManyToMany relationship with Doctors (a patient can see multiple doctors)
 * - mappedBy indicates the owning side of the relationship
 * - @JsonIgnore prevents circular references during JSON serialization
 */
@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nationalId; // Digital ID simulation
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    // ONE-TO-MANY RELATIONSHIP: One Patient can have many Appointments
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Appointment> appointments;
    
    // ONE-TO-MANY RELATIONSHIP: One Patient can have many Medical Records
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MedicalRecord> medicalRecords;
    
    // MANY-TO-MANY RELATIONSHIP: Patients can see multiple Doctors
    // mappedBy = "patients" means Doctor entity owns the relationship
    @ManyToMany(mappedBy = "patients", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Doctor> doctors;
}
