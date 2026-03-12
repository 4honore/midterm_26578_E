package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PatientRepository - Data access layer for Patient entity
 * 
 * EXPLANATION:
 * 
 * existsByNationalId(String nationalId):
 * - Checks if a patient with the given National ID already exists
 * - Returns boolean: true if exists, false otherwise
 * - Used for validation to prevent duplicate patient registration
 * - Spring Data JPA automatically generates the query:
 *   SELECT COUNT(*) > 0 FROM patients WHERE national_id = ?
 * - This is crucial for the Digital ID simulation requirement
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    // EXAM REQUIREMENT: existBy() method to check duplicate National ID
    boolean existsByNationalId(String nationalId);
}
