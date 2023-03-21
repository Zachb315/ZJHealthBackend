package com.ZJHealth.ZJHealth.controller;

import com.ZJHealth.ZJHealth.dto.*;
import com.ZJHealth.ZJHealth.model.Insurance;
import com.ZJHealth.ZJHealth.model.ZJUser;
import com.ZJHealth.ZJHealth.repository.ZJUserRepository;
import com.ZJHealth.ZJHealth.service.ZJUserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:3000")
@Data
public class ZJUserController {
    private final ZJUserService zjUserService;
    private final ZJUserRepository zjUserRepository;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @PostMapping("/register")
    public void register(@RequestBody RegistrationDto register) throws IOException {
        ZJUser user = register.getUser();
        Insurance insurance = register.getInsurance();
        zjUserService.register(user, insurance);
    }

    @GetMapping("/list")
    public List<ZJUser> listall() {
        List<ZJUser> users = zjUserService.listall();
        return users;
    }

    @PostMapping("/login")
    public ResponseEntity<ZJUser> login(@RequestBody ZJUser user) throws ServletException, IOException {
        try {
            request.login(user.getEmail(), user.getPassword());
            ZJUser returnUser = zjUserRepository.findByEmailIgnoreCase(user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(returnUser);
        }
        catch (ServletException e){
            HttpHeaders headers = new HttpHeaders();
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).headers(headers).body(null);
        }
    }

    @GetMapping("/find/{id}")
    public ZJUser findUser(@PathVariable Long id) {
        ZJUser user = zjUserService.findUser(id);
        return user;

    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        zjUserService.deleteUser(id);
    }

    @GetMapping("/role")
    public ResponseEntity<String> getRole() {
        String role = zjUserService.getRole();
        return ResponseEntity.status(HttpStatus.OK).body(role);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @PutMapping("/update")
    public void setPatient(@RequestBody DoctorUpdateDto email) {
        zjUserService.setPatient(email);
    }

    @PostMapping("/forgot")
    public void forgotPassword(@RequestBody ForgotPasswordDto email) {
        zjUserService.forgotPassword(email);

    }

    @GetMapping("/forgot/{token}")
    public void forgotPasswordURL(@PathVariable String token) {
        zjUserService.forgotPasswordURL(token);
    }

    @PostMapping("/forgot/{token}")
    public void forgotPasswordSubmit(@PathVariable String token, @RequestBody ForgotPasswordSubmitDto password) {
        zjUserService.forgotPasswordSubmit(token, password);
    }

    @PreAuthorize("hasAuthority('administrator')")
    @GetMapping("/admin/statistics")
    public ResponseEntity<StatisticsDto> getAdminStats() {
        return ResponseEntity.status(HttpStatus.OK).body(zjUserService.getAdminStats());
    }


}
