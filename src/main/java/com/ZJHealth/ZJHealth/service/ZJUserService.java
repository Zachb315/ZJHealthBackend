package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.dto.DoctorUpdateDto;
import com.ZJHealth.ZJHealth.dto.ForgotPasswordDto;
import com.ZJHealth.ZJHealth.dto.ForgotPasswordSubmitDto;
import com.ZJHealth.ZJHealth.dto.StatisticsDto;
import com.ZJHealth.ZJHealth.model.*;
import com.ZJHealth.ZJHealth.repository.InsuranceRepository;
import com.ZJHealth.ZJHealth.repository.MedicalRecordRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZJUserService implements UserDetailsService {
    private final ZJUserRepository zjUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final InsuranceRepository insuranceRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JavaMailSender javaMailSender;

    public void register(ZJUser user, Insurance insurance) {

        if (zjUserRepository.findByEmailIgnoreCase(user.getEmail())==null) {
            MedicalRecord record = new MedicalRecord();
            user.setCreated(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("patient");
            user.setInsurance(insurance);
            insurance.setUser(user);
            zjUserRepository.save(user);
            insuranceRepository.save(insurance);
            record.setPatient(user);
            medicalRecordRepository.save(record);
            user.setRecord(record);
            zjUserRepository.save(user);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("zjhealth400@gmail.com");
            message.setTo(user.getEmail());
            message.setSubject("Account Created For ZJHealth");
            message.setText("You created an account with ZJHealth.");
            javaMailSender.send(message);

        }
        else {
            throw new IllegalStateException("User exists");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(email);
        return user;

    }

    public List<ZJUser> listall() {
        return zjUserRepository.findAll();
    }

    public ZJUser findUser(Long id) {
        return zjUserRepository.findById(id).get();
    }


    public void deleteUser(Long id) {
        ZJUser user = zjUserRepository.findById(id).get();
        for (Appointment appt : user.getAppointments()) {
            appt.setPatient(null);
        }
        zjUserRepository.deleteById(id);
    }

    public String getRole() {
        Principal currentUser = request.getUserPrincipal();
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(currentUser.getName());
        return user.getRole();
    }

    public void setPatient(DoctorUpdateDto email) {
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(email.getEmail());
        user.setDoctor(null);
        user.setRole("patient");
        zjUserRepository.save(user);
    }

    public void forgotPassword(ForgotPasswordDto email) {
        ZJUser user = zjUserRepository.findByEmailIgnoreCase(email.getEmail());
        String token = UUID.randomUUID().toString();
        ZJUser check = zjUserRepository.findByToken(token);
        while (check!=null) {
            token = UUID.randomUUID().toString();
            check = zjUserRepository.findByToken(token);
        }
        user.setToken(token);
        zjUserRepository.save(user);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zjhealth400@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Reset Request");
        message.setText("Here is the url: http://localhost:3000/forgot/"+user.getToken());
        javaMailSender.send(message);
    }

    public void forgotPasswordURL(String token) {
        ZJUser user = zjUserRepository.findByToken(token);
        if (user==null) {
            throw new IllegalStateException("Invalid Token");
        }

    }

    public void forgotPasswordSubmit(String token, ForgotPasswordSubmitDto password) {
        ZJUser user = zjUserRepository.findByToken(token);
        user.setPassword(passwordEncoder.encode(password.getNewPassword()));
        user.setToken(null);
        zjUserRepository.save(user);
    }

    public StatisticsDto getAdminStats() {
        List<ZJUser> users = zjUserRepository.findAll();
        StatisticsDto retDto = new StatisticsDto();
        for (ZJUser curr : users) {
            if (!curr.getRole().equals("patient")) {
                continue;
            }
            retDto.addState(curr.getState());
            retDto.addAge(curr.getAge());
            for (Diagnosis d : curr.getRecord().getDiagnosis()) {
                retDto.addDiagnosis(d.getDiagnosisName());
            }

        }
        return retDto;
    }
}
