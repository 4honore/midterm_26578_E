package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * LocationRepository - Data access layer for Location entity
 * 
 * EXPLANATION:
 * - Includes methods to retrieve locations by name, type, and parent to build the hierarchy.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    // Hierarchy builder queries
    Optional<Location> findByNameAndLocationTypeAndParentIsNull(String name, String locationType);
    Optional<Location> findByNameAndLocationTypeAndParent(String name, String locationType, Location parent);
}
