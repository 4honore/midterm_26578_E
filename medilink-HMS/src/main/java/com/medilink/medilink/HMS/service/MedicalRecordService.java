package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.MedicalRecord;
import com.medilink.medilink.HMS.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────


// ─── Implementation ──────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }
public MedicalRecord getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Medical Record not found with id: " + id));
    }
public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }
public Page<MedicalRecord> getAllMedicalRecordsPaginated(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable);
    }
public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecord) {
        MedicalRecord existingRecord = getMedicalRecordById(id);
        existingRecord.setDiagnosis(medicalRecord.getDiagnosis());
        existingRecord.setTreatment(medicalRecord.getTreatment());
        existingRecord.setNotes(medicalRecord.getNotes());
        existingRecord.setRecordDate(medicalRecord.getRecordDate());
        existingRecord.setPatient(medicalRecord.getPatient());
        return medicalRecordRepository.save(existingRecord);
    }
public void deleteMedicalRecord(Long id) {
        MedicalRecord medicalRecord = getMedicalRecordById(id);
        medicalRecordRepository.delete(medicalRecord);
    }
}


