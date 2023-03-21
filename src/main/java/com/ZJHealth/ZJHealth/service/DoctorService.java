package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.AppointmentReturnDto;
import com.ZJHealth.ZJHealth.dto.DirectoryDto;
import com.ZJHealth.ZJHealth.dto.UpdateDoctorDto;
import com.ZJHealth.ZJHealth.model.Appointment;
import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.model.Doctor;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.AppointmentRepository;
import com.ZJHealth.ZJHealth.repository.DepartmentRepository;
import com.ZJHealth.ZJHealth.repository.DoctorRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ZJUserRepository zjUserRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;
    @Autowired
    private HttpServletRequest request;

    public void updateRole(UpdateDoctorDto doctor) throws IOException {
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(doctor.getEmail());
        Department dept = departmentRepository.findByNameIgnoreCase(doctor.getDeptName());
        if (dept==null) {
            throw new IllegalStateException("Dept does not exist");
        }
        Doctor docInfo = new Doctor();

        File file = new File("src\\main\\resources\\static\\images\\"
                +doctor.getEmail()+doctor.getPhotoPath().getOriginalFilename());
        FileOutputStream output = new FileOutputStream(file);
        output.write(doctor.getPhotoPath().getBytes());

        docInfo.setPhotoPath(doctor.getPhotoPath().getOriginalFilename());
        docInfo.setSpecialty(doctor.getSpecialty());
        docInfo.setSalary(doctor.getSalary());
        docInfo.setDepartment(dept);
        user.setDoctor(docInfo);
        user.setRole("doctor");
        zjUserRepository.save(user);
    }


    public Doctor updateDepartment(Long docId, Long deptId) {
        Doctor doctor = zjUserRepository.findById(docId).get().getDoctor();
        Department department = departmentRepository.findById(deptId).get();
        doctor.setDepartment(department);
        doctorRepository.save(doctor);
        return doctor;
    }


    public List<ZJUser> listAll() {
        return zjUserRepository.findAllByDoctorIsNotNull();
    }

    public List<ZJUser> findDoctor(String name) {
        List<ZJUser> doctors = new ArrayList<>();
        List<ZJUser> getDoctors = zjUserRepository.findAllByDoctorIsNotNull();
        if (name.contains(" ")) {
            String[] split = name.split(" ");
            String firstName = split[0];
            String lastName = split[1];
            for (int i=0; i<getDoctors.size(); i++) {
                ZJUser current = getDoctors.get(i);
                if (current.getFirstName().toLowerCase().contains(firstName.toLowerCase()) ||
                        current.getLastName().toLowerCase().contains(lastName.toLowerCase()) &&
                        !doctors.contains(current)) {

                    doctors.add(getDoctors.get(i));

                }
            }
        }
        else {
            for (int i=0; i<getDoctors.size(); i++) {
                ZJUser current = getDoctors.get(i);
                if (current.getFirstName().toLowerCase().contains(name.toLowerCase()) ||
                        current.getLastName().toLowerCase().contains(name.toLowerCase()) &&
                        !doctors.contains(current)) {

                    doctors.add(getDoctors.get(i));
                }
            }
        }

        return doctors;


    }

    public List<AppointmentReturnDto> listAppointments() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        List<AppointmentReturnDto> convertAppts = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(user.getDoctor().getId());
        for (Appointment appt : appointments) {
            if (appt.getDiagnosis()!=null) {
                continue;
            }
            AppointmentReturnDto dto = new AppointmentReturnDto();
            dto.setId(appt.getId());
            dto.setPatient(appt.getPatient());
            dto.setDoctor(appt.getDoctor().getUser());
            dto.setDate(appt.getDate());
            dto.setReview(appt.getReview());
            dto.setType(appt.getType());
            dto.setIntake(appt.getAppointmentIntake());
            convertAppts.add(dto);
        }
        return convertAppts;
    }

    public List<AppointmentReturnDto> doctorStatistics() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        List<AppointmentReturnDto> convertAppts = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAllByDoctorId(user.getDoctor().getId());
        for (Appointment appt : appointments) {
            AppointmentReturnDto dto = new AppointmentReturnDto();
            dto.setDoctor(appt.getDoctor().getUser());
            dto.setDiagnosis(appt.getDiagnosis());
            convertAppts.add(dto);
        }
        return convertAppts;

    }
}
