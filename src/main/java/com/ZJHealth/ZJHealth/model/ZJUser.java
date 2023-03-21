package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class ZJUser implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Integer age;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String token;
    private int zip;
    private String phoneNum;
    private LocalDateTime created;


    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user_doctor")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user_insurance")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient")
    @JsonManagedReference(value = "user_appt")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToOne
    @JsonManagedReference(value = "user_record")
    private MedicalRecord record;

    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public ZJUser(long id, String email) {
        this.id=id;
        this.email=email;
    }
}
