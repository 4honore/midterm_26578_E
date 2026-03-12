package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.Patient;
import com.medilink.medilink.HMS.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────


// ─── Implementation ──────────────────────────────────────────────────────────

/**
 * PatientServiceImpl - Business logic for Patient operations
 *
 * EXPLANATION OF VALIDATION:
 * - Before saving a patient, we check if National ID already exists
 * - existsByNationalId() returns true if a patient with that ID exists
 * - This prevents duplicate patient registration
 * - Throws RuntimeException if duplicate is found
 * - This demonstrates proper validation in the service layer
 */
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
public Patient savePatient(Patient patient) {
        // EXAM REQUIREMENT: Validate duplicate National ID before saving
        // existsByNationalId checks if a patient with this National ID already exists
        if (patientRepository.existsByNationalId(patient.getNationalId())) {
            throw new RuntimeException("Patient with National ID " + patient.getNationalId() + " already exists");
        }
        return patientRepository.save(patient);
    }
public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }
public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
public Page<Patient> getAllPatientsPaginated(Pageable pageable) {
        return patientRepository.findAll(pageable);
    }
public Patient updatePatient(Long id, Patient patient) {
        Patient existingPatient = getPatientById(id);
        // Don't allow changing National ID
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        return patientRepository.save(existingPatient);
    }
public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}


