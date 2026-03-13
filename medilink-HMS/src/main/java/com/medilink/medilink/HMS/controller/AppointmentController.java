package com.medilink.medilink.HMS.controller;

import com.medilink.medilink.HMS.dto.ApiResponse;
import com.medilink.medilink.HMS.dto.AppointmentSummaryDTO;
import com.medilink.medilink.HMS.entity.Appointment;
import com.medilink.medilink.HMS.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AppointmentController - REST API endpoints for Appointment management
 * 
 * EXPLANATION:
 * - Handles all appointment-related HTTP requests
 * - Returns clean, formatted responses using DTOs
 * - Supports pagination with user-friendly format
 * - Provides both detailed and summary views
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody Appointment appointment) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.saveAppointment(appointment));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AppointmentSummaryDTO>> getAll(Pageable pageable) {
        Page<Appointment> appointmentPage = appointmentService.getAllAppointmentsPaginated(pageable);
        
        // Convert to summary DTOs
        List<AppointmentSummaryDTO> summaries = appointmentPage.getContent().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
        
        // Create pagination info
        ApiResponse.PaginationInfo pagination = new ApiResponse.PaginationInfo(
                appointmentPage.getNumber(),
                appointmentPage.getTotalPages(),
                appointmentPage.getTotalElements(),
                appointmentPage.getSize(),
                appointmentPage.hasNext(),
                appointmentPage.hasPrevious()
        );
        
        ApiResponse<AppointmentSummaryDTO> response = new ApiResponse<>(
                "Appointments retrieved successfully",
                summaries,
                pagination
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable Long id,
                                              @Valid @RequestBody Appointment appointment) {
        return ResponseEntity.ok(appointmentService.updateAppointment(id, appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Convert Appointment entity to summary DTO for clean API responses
     */
    private AppointmentSummaryDTO convertToSummaryDTO(Appointment appointment) {
        return new AppointmentSummaryDTO(
                appointment.getId(),
                appointment.getAppointmentDate(),
                appointment.getStatus(),
                appointment.getPatient().getId(),
                appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName(),
                appointment.getPatient().getPhone(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getName(),
                appointment.getDoctor().getSpecialization(),
                appointment.getLocation().getId(),
                appointment.getLocation().getName(),
                appointment.getLocation().getHospitalName()
        );
    }
}
