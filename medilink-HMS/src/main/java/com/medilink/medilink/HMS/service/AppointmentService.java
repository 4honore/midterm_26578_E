package com.medilink.medilink.HMS.service;

import com.medilink.medilink.HMS.entity.Appointment;
import com.medilink.medilink.HMS.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// ─── Interface ───────────────────────────────────────────────────────────────


// ─── Implementation ──────────────────────────────────────────────────────────
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }
public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
public Page<Appointment> getAllAppointmentsPaginated(Pageable pageable) {
        return appointmentRepository.findAll(pageable);
    }
public Appointment updateAppointment(Long id, Appointment appointment) {
        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setAppointmentDate(appointment.getAppointmentDate());
        existingAppointment.setStatus(appointment.getStatus());
        existingAppointment.setPatient(appointment.getPatient());
        existingAppointment.setDoctor(appointment.getDoctor());
        existingAppointment.setLocation(appointment.getLocation());
        return appointmentRepository.save(existingAppointment);
    }
public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
    }
}


