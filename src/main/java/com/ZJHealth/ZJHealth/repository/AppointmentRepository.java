package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.ZJUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctorIsNull();
    Appointment findByPatient(ZJUser patient);
    List<Appointment> findAllByPatientId(Long id);
    List<Appointment> findAllByDoctorId(Long id);
    List<Appointment> findAllByDiagnosisIsNullAndDoctorId(Long id);
    List<Appointment> findAllByReviewIsNotNull();


}
