package com.medilink.medilink.HMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Appointment Entity - Represents appointments between patients and doctors
 * 
 * EXPLANATION:
 * - Links Patient, Doctor, and Location together
 * - @ManyToOne relationships: Many appointments can belong to one patient/doctor/location
 * - @JoinColumn specifies the foreign key column names
 * - Status can be: SCHEDULED, COMPLETED, CANCELLED
 * - appointmentDate stores both date and time using LocalDateTime
 */
@Entity
@Table(name = "appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDate;
    
    @Column(nullable = false)
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    
    // MANY-TO-ONE RELATIONSHIP: Many Appointments belong to One Patient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    // MANY-TO-ONE RELATIONSHIP: Many Appointments belong to One Doctor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
    
    // MANY-TO-ONE RELATIONSHIP: Many Appointments belong to One Location
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
}
