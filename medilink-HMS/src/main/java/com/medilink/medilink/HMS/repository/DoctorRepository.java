package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DoctorRepository - Data access layer for Doctor entity
 * 
 * EXPLANATION:
 * - Provides CRUD operations for Doctor entity
 * - Inherits methods like save(), findById(), findAll(), delete()
 * - Can be extended with custom query methods if needed
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Inherits all standard CRUD operations from JpaRepository
}
