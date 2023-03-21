package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.UpdateDoctorDto;
import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.model.Doctor;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.AppointmentRepository;
import com.ZJHealth.ZJHealth.repository.DepartmentRepository;
import com.ZJHealth.ZJHealth.repository.DoctorRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    private ZJUserRepository zjUserRepository;
    @Mock
    private DoctorRepository doctorRepository;
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private DepartmentRepository departmentRepository;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorService = new DoctorService(doctorRepository, zjUserRepository, departmentRepository, appointmentRepository);
    }

    @Test
    void updateRole() throws IOException {
        ZJUser user = new ZJUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRole("doctor");
        user.setZip(0);
        UpdateDoctorDto doc = new UpdateDoctorDto();
        doc.setEmail(user.getEmail());
        doc.setSalary(123);
        doc.setDeptName("dept");
        doc.setSpecialty("specialty");
        Doctor convertToDoc = new Doctor();
        convertToDoc.setUser(user);
        convertToDoc.setSalary(doc.getSalary());
        convertToDoc.setSpecialty(doc.getSpecialty());
        convertToDoc.setId(2L);
        user.setDoctor(convertToDoc);
        when(zjUserRepository.findByEmailIgnoreCase(user.getEmail())).thenReturn(user);
        when(departmentRepository.findByNameIgnoreCase(doc.getDeptName())).thenReturn(null);
        doctorService.updateRole(doc);
        verify(zjUserRepository).save(user);
    }

    @Test
    void updateDepartment() {

    }
}