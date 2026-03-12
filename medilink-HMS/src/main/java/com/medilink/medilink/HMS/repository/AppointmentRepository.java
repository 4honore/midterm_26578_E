package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AppointmentRepository - Data access layer for Appointment entity
 * 
 * EXPLANATION:
 * - Manages appointment data persistence
 * - Supports pagination and sorting through JpaRepository
 * - Can query appointments by patient, doctor, or location
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Inherits all standard CRUD operations from JpaRepository
}
