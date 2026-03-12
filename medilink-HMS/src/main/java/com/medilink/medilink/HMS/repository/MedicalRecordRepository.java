package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MedicalRecordRepository - Data access layer for MedicalRecord entity
 * 
 * EXPLANATION:
 * - Handles medical record data operations
 * - Provides access to patient medical history
 * - Supports pagination for large medical record datasets
 */
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    // Inherits all standard CRUD operations from JpaRepository
}
