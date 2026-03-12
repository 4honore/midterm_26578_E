package com.medilink.medilink.HMS.controller;

import com.medilink.medilink.HMS.entity.MedicalRecord;
import com.medilink.medilink.HMS.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MedicalRecordController - REST API endpoints for Medical Record management
 * 
 * EXPLANATION:
 * - Manages patient medical records and history
 * - Each record contains diagnosis, treatment, and notes
 * - Linked to specific patients via @ManyToOne relationship
 * - Supports pagination for viewing large medical histories
 */
@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecord> create(@RequestBody MedicalRecord record) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicalRecordService.saveMedicalRecord(record));
    }

    @GetMapping
    public ResponseEntity<Page<MedicalRecord>> getAll(Pageable pageable) {
        return ResponseEntity.ok(medicalRecordService.getAllMedicalRecordsPaginated(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getById(@PathVariable Long id) {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(@PathVariable Long id,
                                                @RequestBody MedicalRecord record) {
        return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(id, record));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        medicalRecordService.deleteMedicalRecord(id);
        return ResponseEntity.noContent().build();
    }
}
