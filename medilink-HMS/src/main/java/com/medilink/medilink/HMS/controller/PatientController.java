package com.medilink.medilink.HMS.controller;

import com.medilink.medilink.HMS.entity.Patient;
import com.medilink.medilink.HMS.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

/**
 * PatientController - REST API endpoints for Patient management
 * 
 * EXPLANATION:
 * - Handles all patient-related HTTP requests
 * - POST endpoint validates National ID before creating patient
 * - Supports pagination and sorting for large datasets
 * - Returns appropriate HTTP status codes (201 for created, 200 for success, 204 for delete)
 * - @Valid annotation triggers validation on request body
 */
@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> create(@Valid @RequestBody Patient patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.savePatient(patient));
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> getAll(Pageable pageable) {
        return ResponseEntity.ok(patientService.getAllPatientsPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id,
                                          @Valid @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
