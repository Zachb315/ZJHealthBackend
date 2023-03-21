package com.ZJHealth.ZJHealth;

import com.ZJHealth.ZJHealth.model.Department;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.DepartmentRepository;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.ZJHealth.ZJHealth.service.ZJUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class ZjHealthApplication {
	private final ZJUserRepository zjUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final DepartmentRepository departmentRepository;
	@Autowired
	private ZJUserService zjUserService;

	public static void main(String[] args) {


		SpringApplication.run(ZjHealthApplication.class, args);

	}

	@Bean
	public void initAdmin() {
		if (zjUserRepository.findByEmailIgnoreCase("root@zjhealthtest.com")==null) {
			ZJUser root = new ZJUser();
			root.setRole("administrator");
			root.setPassword(passwordEncoder.encode("root"));
			root.setEmail("root@zjhealthtest.com");
			root.setCity("N/A");
			root.setState("N/A");
			root.setFirstName("admin");
			root.setLastName("admin");
			root.setPhoneNum("N/A");
			root.setCreated(LocalDateTime.now());
			root.setStreet("N/A");
			root.setZip(0);
			zjUserRepository.save(root);
			Department radiology = new Department();
			radiology.setName("Radiology");
			radiology.setChair("John Sue");
			departmentRepository.save(radiology);
			Department surgery = new Department();
			surgery.setChair("Mike Blue");
			surgery.setName("Surgery");
			departmentRepository.save(surgery);
		}
	}




}
