package com.medilink.medilink.HMS.repository;

import com.medilink.medilink.HMS.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserRepository - Data access layer for User entity
 * 
 * EXPLANATION OF CUSTOM QUERY METHODS:
 * 
 * 1. existsByUsername(String username):
 *    - Returns true if a user with the given username exists
 *    - Used for validation before creating new users
 * 
 * 2. findUsersByProvinceName(String provinceName):
 *    - Finds all users in a specific province by traversing the Location hierarchy.
 *    - User -> Village (location) -> Cell (parent) -> Sector (parent) -> District (parent) -> Province (parent)
 *    - Demonstrates complex JPQL traversing self-referencing relationships.
 * 
 * 3. findUsersByProvinceCode(String provinceCode):
 *    - Similar to above but uses province code instead.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // EXAM REQUIREMENT: existBy() method for validation
    boolean existsByUsername(String username);
    
    // EXAM REQUIREMENT: Retrieve users by province name
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.name = :provinceName")
    List<User> findUsersByProvinceName(@Param("provinceName") String provinceName);
    
    // EXAM REQUIREMENT: Retrieve users by province code
    @Query("SELECT u FROM User u WHERE u.location.parent.parent.parent.parent.code = :provinceCode")
    List<User> findUsersByProvinceCode(@Param("provinceCode") String provinceCode);
}
