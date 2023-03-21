package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String type;
    private int rating;
    private String review;



    @ManyToOne
    @JsonBackReference(value = "user_appt")
    private ZJUser patient;

    @ManyToOne
    @JsonBackReference(value = "doctor_appt")
    private Doctor doctor;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference(value = "appt_intake")
    private AppointmentIntake appointmentIntake;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "dia_appt")
    private Diagnosis diagnosis;

}
