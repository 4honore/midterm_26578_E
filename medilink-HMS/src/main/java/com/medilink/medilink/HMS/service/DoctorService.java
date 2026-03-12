package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.Doctor;
import com.medilink.medilink.HMS.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────


// ─── Implementation ──────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }
public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
public Page<Doctor> getAllDoctorsPaginated(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
public Doctor updateDoctor(Long id, Doctor doctor) {
        Doctor existingDoctor = getDoctorById(id);
        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setLocation(doctor.getLocation());
        return doctorRepository.save(existingDoctor);
    }
public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepository.delete(doctor);
    }
}


