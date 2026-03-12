package com.medilink.medilink.HMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MedicalRecord Entity - Stores patient medical history and diagnoses
 * 
 * EXPLANATION:
 * - Each medical record belongs to one patient
 * - @ManyToOne relationship: Many records can belong to one patient
 * - Stores diagnosis, treatment plan, and additional notes
 * - recordDate tracks when the record was created
 * - @JoinColumn creates foreign key "patient_id" linking to patients table
 */
@Entity
@Table(name = "medical_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 1000)
    private String diagnosis;
    
    @Column(nullable = false, length = 1000)
    private String treatment;
    
    @Column(length = 2000)
    private String notes;
    
    @Column(nullable = false)
    private LocalDateTime recordDate;
    
    // MANY-TO-ONE RELATIONSHIP: Many Medical Records belong to One Patient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
}
