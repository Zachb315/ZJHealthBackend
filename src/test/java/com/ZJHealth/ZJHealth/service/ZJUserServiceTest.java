package com.ZJHealth.ZJHealth.service;

import com.ZJHealth.ZJHealth.config.TestConfig;
import com.ZJHealth.ZJHealth.model.Insurance;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.InsuranceRepository;
import com.ZJHealth.ZJHealth.repository.MedicalRecordRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@Import(TestConfig.class)
public class ZJUserServiceTest {
    @Mock
    private ZJUserRepository zjUserRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;
    private ZJUserService zjUserService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private InsuranceRepository insuranceRepository;

    @BeforeEach
    void setup() {
        zjUserService = new ZJUserService(zjUserRepository, passwordEncoder, insuranceRepository, medicalRecordRepository);

    }

    @Test
    void register() {
        ZJUser user = new ZJUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole("role");
        user.setZip(0);
        Insurance insurance = new Insurance();
        insurance.setUser(user);
        insurance.setInsuranceName("insurance");
        user.setInsurance(insurance);
        zjUserService.register(user, insurance);
        verify(zjUserRepository).save(user);
        verify(insuranceRepository).save(insurance);
    }

    @Test
    void listall() {
        zjUserService.listall();
        verify(zjUserRepository).findAll();
    }

    @Test
    void deleteUser() {
        ZJUser user = new ZJUser();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("temp");
        when(zjUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        zjUserService.deleteUser(user.getId());
        verify(zjUserRepository).deleteById(user.getId());

    }
}