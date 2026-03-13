package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
 * - Comprehensive validation ensures data integrity
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
    
    @NotBlank(message = "National ID is required")
    @Size(min = 16, max = 16, message = "National ID must be exactly 16 digits")
    @Pattern(regexp = "^[0-9]{16}$", message = "National ID must contain only digits")
    @Column(nullable = false, unique = true)
    private String nationalId; // Digital ID simulation
    
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "First name must contain only letters and spaces")
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Last name must contain only letters and spaces")
    @Column(nullable = false)
    private String lastName;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(078|079|072|073)[0-9]{7}$", message = "Phone must be valid Rwanda format (078/079/072/073 + 7 digits)")
    @Column(nullable = false)
    private String phone;
    
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
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
