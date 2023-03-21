package com.ZJHealth.ZJHealth.repository;

import com.ZJHealth.ZJHealth.model.ZJUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ZJUserRepository extends JpaRepository<ZJUser, Long> {
    ZJUser findByEmailIgnoreCase(String email);
    List<ZJUser> findAllByDoctorIsNotNull();
    ZJUser findByToken(String token);

}
