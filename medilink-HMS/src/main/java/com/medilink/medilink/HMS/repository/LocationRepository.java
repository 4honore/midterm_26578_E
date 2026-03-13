package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * LocationRepository - Data access layer for Location entity
 * 
 * EXPLANATION:
 * - Methods to find locations by name, type, and parent for building hierarchy
 * - Supports the Adjacency List Model for hierarchical data
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
    // Find root locations (Provinces have no parent)
    Optional<Location> findByNameAndLocationTypeAndParentIsNull(String name, Location.LocationType locationType);
    
    // Find child locations
    Optional<Location> findByNameAndLocationTypeAndParent(String name, Location.LocationType locationType, Location parent);
}
