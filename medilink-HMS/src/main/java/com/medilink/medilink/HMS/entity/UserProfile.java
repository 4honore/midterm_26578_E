package com.medilink.medilink.HMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * UserProfile Entity - Extended details for a User
 * 
 * EXPLANATION:
 * - This entity fulfills the ONE-TO-ONE relationship requirement.
 * - @OneToOne establishes a 1-to-1 link between User and UserProfile.
 * - Stores optional biographical data like email, bio, and alternative phone.
 */
@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 150)
    private String email;
    
    @Column(length = 20)
    private String altPhone;
    
    // ONE-TO-ONE RELATIONSHIP: One User has exactly One UserProfile
    // mappedBy indicates that the User entity owns the relationship
    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private User user;
}
