package com.medilink.medilink.HMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * AppointmentSummaryDTO - Simplified appointment view for API responses
 * 
 * EXPLANATION:
 * - Provides clean, readable appointment data without nested complexity
 * - Includes essential information without circular references
 * - Makes API responses more user-friendly
 * - Reduces payload size by excluding unnecessary nested objects
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentSummaryDTO {
    
    private Long id;
    private LocalDateTime appointmentDate;
    private String status;
    
    // Patient summary
    private Long patientId;
    private String patientName;
    private String patientPhone;
    
    // Doctor summary
    private Long doctorId;
    private String doctorName;
    private String doctorSpecialization;
    
    // Location summary
    private Long locationId;
    private String locationName;
    private String hospitalName;
}